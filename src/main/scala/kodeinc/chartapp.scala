package kodeinc

import akka.NotUsed
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Terminated}
import akka.actor.typed.scaladsl.Behaviors

object chartapp {

  object ChatRoom{
    sealed trait RoomCommand
    case class GetSession(screenNAme:String) extends RoomCommand
    case class PublishMessage(screenNAme:String,message:String) extends RoomCommand

    // so when u initialize the room command,sessions are empty.
    def apply(): Behavior[RoomCommand] =   chatMeRoom(List.empty)

    def chatMeRoom(sessions:List[ActorRef[SessionCommand]]):Behavior[RoomCommand] =  Behaviors.receive{ (ctx,msg)=>
      msg match {
        case GetSession(screenNAme) => ???
        case PublishMessage(screenNAme, message) => ???
      }
    }

    sealed trait SessionEvent
    case class SessionGranted(handler:ActorRef[PostMessage]) extends SessionEvent
    case class SessionDenied(reason:String) extends  SessionEvent
    case class MessagePosted(screenName:String,message:String)extends  SessionEvent

    sealed trait SessionCommand
    case class PostMessage(message:String) extends  SessionCommand
    case class NotifyClient(message:MessagePosted) extends  SessionCommand

    def session():Behavior[SessionCommand] = Behaviors.receiveMessage{
      ???
    }

  }
  object Main{
    def apply(): Behavior[NotUsed] =  Behaviors.setup{ ctx =>
      ctx.log.info("The Applicatin Main Has Started")

      Behaviors.receiveSignal{
        case(_,Terminated(_)) =>
           Behaviors.stopped
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val system = ActorSystem(Main(),"ChartApp")
    println("Believe me")
  }
}
