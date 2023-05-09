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
  case class delete(a:Int,b:Int,response: ActorRef[Response]) extends Calculator
  case class devide(a:Int,b:Int,response: ActorRef[Response]) extends Calculator
  case class multiply(a:Int,b:Int,response: ActorRef[Response]) extends Calculator

  object   CalcA{
    def apply(): Behavior[Calculator] = Behaviors.receiveMessage{ msg =>
      msg match {
        case add(a, b, response)=>{
         val result = a+b
          response tell success(result.toString)
         Behaviors.same
        }
        case delete(a, b, response) => {
          val result = a - b
          response tell success(result.toString)
          Behaviors.same
        }
        case devide(a, b, response) => {
          val result = a / b
          response tell success(result.toString)
          Behaviors.same
        }
        case multiply(a, b, response) => {
          val result = a * b
          response tell success(result.toString)
          Behaviors.same
        }
      }


    }
  }

  object Main{
    def apply(values:calc):Behavior[Response]  = Behaviors.setup{ctx =>
      ctx.log.info("Entered")
      val calcApp = ctx.spawn(CalcA(),"calculator_application")

      values.option match {
        case kodeinc.akkaCalc.calOptions.ADD =>  calcApp tell add(values.a,values.b,ctx.self)
        case kodeinc.akkaCalc.calOptions.DELETE =>  calcApp tell delete(values.a,values.b,ctx.self)
        case kodeinc.akkaCalc.calOptions.DEVIDE =>  calcApp tell devide(values.a,values.b,ctx.self)
        case kodeinc.akkaCalc.calOptions.MULTIPLY =>  calcApp tell multiply(values.a,values.b,ctx.self)
      }

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
            ctx.log.info("No Option Selectetd")
            Behaviors.same
          }

        }

      }
    }
  }

  def main(args: Array[String]): Unit = {
    val ct = calc(676,34,calOptions.DEVIDE);
    val ct2 = calc(676,34,calOptions.ADD);
    val ct3 = calc(676,34,calOptions.DELETE);
    val ct4 = calc(676,34,calOptions.MULTIPLY);
    ActorSystem(Main(ct),"Main")
    ActorSystem(Main(ct2),"Main")
    ActorSystem(Main(ct3),"Main")
    ActorSystem(Main(ct4),"Main")


  }
}
