package kodeinc

import akka.NotUsed
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Terminated}
import akka.actor.typed.scaladsl.Behaviors

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object chartapp {

  object ChatRoom{
    sealed trait RoomCommand
    case class GetSession(screenNAme:String,client:ActorRef[SessionEvent]) extends RoomCommand
    case class PublishMessage(screenNAme:String,message:String) extends RoomCommand

    // so when u initialize the room command,sessions are empty.
    def apply(): Behavior[RoomCommand] =   chatMeRoom(List.empty)

    def chatMeRoom(sessions:List[ActorRef[SessionCommand]]):Behavior[RoomCommand] =  Behaviors.receive{ (ctx,msg)=>
      msg match {
        case GetSession(screenNAme,client) =>
          val ses  = ctx.spawn(
            session(ctx.self,screenNAme,client)
            ,name=URLEncoder.encode(screenNAme,StandardCharsets.UTF_8.name
            ))

          client ! SessionGranted(ses)
          chatMeRoom(ses ::sessions)

        case PublishMessage(screenNAme, message) =>
          val notification = NotifyClient(MessagePosted(screenNAme,message))
          sessions.foreach( _! notification)
            Behaviors.same


      }
    }

    sealed trait SessionEvent
    case class SessionGranted(handler:ActorRef[PostMessage]) extends SessionEvent
    case class SessionDenied(reason:String) extends  SessionEvent
    case class MessagePosted(screenName:String,message:String)extends  SessionEvent

    sealed trait SessionCommand
    case class PostMessage(message:String) extends  SessionCommand
    case class NotifyClient(message:MessagePosted) extends  SessionCommand

    def session(
               room: ActorRef[PublishMessage]
               ,screenName:String
               ,client:ActorRef[SessionEvent]
               ):Behavior[SessionCommand] = Behaviors.receiveMessage{
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
