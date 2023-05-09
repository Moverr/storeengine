package kodeinc

import akka.NotUsed
import akka.actor.typed.{ActorSystem, Behavior, Terminated}
import akka.actor.typed.scaladsl.Behaviors

object chartapp {

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
