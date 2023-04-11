package kodeinc

import akka.actor.typed.{ActorSystem, Behavior}
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

    case class RespnseMessage(message:) extends  StoreMessage


    var stores:List[Store] = Nil

    def apply():Behavior[StoreMessage] = Behaviors.receive { (context, message) =>
      message match {
        case Create(newStore) => {
          newStore :: stores
          Behaviors.same
        }
        case GetByName(name) => {
        val storeList= stores.filter(x=>x.name==name)
          Behaviors.same
        }
        case GetById(id) => {
          val storeList = stores.filter(x => x.id == id)
          Behaviors.same

        }
        case Delete(id) => {
          stores = stores.filter(x => x.id != id)
          Behaviors.same
        }
      }


    }
  }
  def main(args: Array[String]): Unit = {
    //define the main entry..
  }
}
