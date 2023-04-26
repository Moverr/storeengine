package kodeinc

import akka.actor.typed.scaladsl.AskPattern.{Askable, schedulerFromActorSystem}
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives.complete
import kodeinc.StoreApp.StoreService.{GetById, Storage, StoreMessage}

import java.util.UUID

object StoreApp {

  implicit  val system = ActorSystem.create(Behaviors.empty,"store-app")

  case class Store(id:UUID,name:String,details:String)
  object StoreService{

    sealed trait StoreMessage
    case class Create(store:Vector[Store]) extends StoreMessage
    case class GetByName(name:String) extends StoreMessage
    case class GetById(replyT:ActorRef[StoreMessage], id:UUID) extends  StoreMessage
    case class Delete(id:UUID) extends  StoreMessage

    case class list(replyTo:ActorRef[StoreMessage]) extends   StoreMessage
    case class RespnseMessage(message:String) extends  StoreMessage



    case class Storage(stores:List[Store])


    var Stores:List[Store] = Nil

    def apply():Behavior[StoreMessage] = Behaviors.receive { (context, message) =>
      message match {
        case Create(newStore) => {
          context.log.info("bessed king")
          newStore :: Stores
          Behaviors.same
        }
        case GetByName(name) => {
          Storage(Stores.filter(x=>x.name==name))
          Behaviors.same
        }
        case GetById(replyTo,id)=> {
          replyTo !  Storage(Stores.filter(x => x.id == id))
          Behaviors.same

        }
        case Delete(id) => {
          Storage(Stores.filter(x => x.id != id))
          Behaviors.same
        }
      }


    }
  }


  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[StoreService.StoreMessage] = ActorSystem(StoreService(), "Blame")
    //define the main entry..
    val auction: ActorRef[StoreService.StoreMessage] = system
    val route = Directives.path("store"){
      Directives.concat{
        Directives.get {
         // system ! "sesee"
          auction.ask(GetById(ActorRef[StoreMessage],UUID.randomUUID()))
          complete(???)
        }
      }

    }


    val binding = Http().newServerAt("localhost",9000).bind(route)
    println("Plain and Simple")
  }
}
