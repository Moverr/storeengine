package kodeinc

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import java.util.UUID

object StoreApp {

  implicit  val system = ActorSystem.create(Behaviors.empty,"store-app")

  case class Store(id:UUID,name:String,details:String)
  object StoreService{

    sealed trait StoreMessage
    case class Create(store:Vector[Store]) extends StoreMessage
    case class GetByName(name:String) extends StoreMessage
    case class GetById(id:UUID) extends  StoreMessage
    case class Delete(id:UUID) extends  StoreMessage

    case class list(replyTo:ActorRef[Storage]) extends   StoreMessage
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
        case GetById(id) => {
          Storage(Stores.filter(x => x.id == id))
          Behaviors.same

        }
        case Delete(id) => {
          Storage(Stores.filter(x => x.id != id)))
          Behaviors.same
        }
      }


    }
  }
  def main(args: Array[String]): Unit = {
    //define the main entry..
    println("Plain and Simple")
  }
}
