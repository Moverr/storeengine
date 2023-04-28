package kodeinc

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import kodeinc.Appli.HelloWorld

object calcapp {

  sealed trait calc

  case class  add[T](a: T, b: T) extends calc

  //sealed trait response
  case class response(msg: String)
  //extends  response


  object calcBot {
    def apply(): Behavior[calc] = Behaviors.receive { (ctx, msg) =>
      msg match {
        case add(a:Int, b:Int)    => {
          val greeter = ctx.spawn(systema(), "greeter")
          val result = a + b;
          greeter.tell(response(" result :: " + result))
          Behaviors.same

        }


      }

    }
  }


  object systema {
    def apply(): Behavior[response] = Behaviors.receive { (ctx, message) =>

      print(message.msg)

      Behaviors.same
    }
  }

  def main(args: Array[String]): Unit = {
    val x: ActorSystem[calc] = ActorSystem(calcBot(), "Blenders")
    x.tell(add(121, 348))


  }
}
