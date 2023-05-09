package kodeinc

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import java.util

object akkaCalc {
  sealed trait Response
  case class error(errorCode:String,msg:String) extends Response
  case class success(msg:String) extends Response

  object calOptions extends Enumeration{
    type Option = Value
    val ADD,DELETE,DEVIDE,MULTIPLY = Value
  }
  case class calc(a:Int,b:Int,option:calOptions.Option )

  sealed trait Calculator
  case class add(a:Int,b:Int,response: ActorRef[Response]) extends Calculator

  object   CalcA{
    def apply(): Behavior[Calculator] = Behaviors.receiveMessage{ msg =>
      msg match {
        case add(a,b,response)=>{
         val result = a+b
          response tell success(result.toString)
         Behaviors.same
        }
      }


    }
  }

  object Main{
    def apply(values:calc):Behavior[Response]  = Behaviors.setup{ctx =>
      ctx.log.info("Entered")
      val calcApp = ctx.spawn(CalcA(),"movevrs")
      calcApp tell add(1,2,ctx.self)
      Behaviors.receiveMessage{ msg =>
        msg match {
          case error(errorCode, msg) =>{
            ctx.log.info("Error Message")
            Behaviors.same
          }
          case success(msg) =>{
            ctx.log.info("Success Message")
            println(msg)
            Behaviors.same

          }
          case _ => {
            ctx.log.info("Interesting steps")
            Behaviors.same
          }

        }

      }
    }
  }

  def main(args: Array[String]): Unit = {
    val ct = calc(12,34,calOptions.ADD);
    val system=  ActorSystem(Main(ct),"Main");
  }
}
