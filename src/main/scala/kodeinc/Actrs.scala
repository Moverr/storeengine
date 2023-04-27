package kodeinc

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, DispatcherSelector}
import kodeinc.Appli.CustomDispatchersExample.HelloWorldMain
import kodeinc.Appli.HelloWorld.{Greet, Greeted}



object  Appli{

  object HelloWorld {

    final case class Greet(whom: String, replyTo: ActorRef[Greeted])

    final case class Greeted(whom: String, from: ActorRef[Greet])

    def apply(): Behavior[Greet] = Behaviors.receive { (ctx, msg) =>
      ctx.log.info("Helloo Rogers")
      //Here am reading the behavior or the data holder..
      //  val msged =msg.whom
      msg.replyTo ! Greeted(msg.whom, ctx.self)
      Behaviors.same
    }


  }


  object HelloworldBot {
    def apply(max: Int): Behavior[HelloWorld.Greeted] = {
      bot(0, max)
    }

    def bot(greetingCounter: Int, max: Int):Behavior[HelloWorld.Greeted]=Behaviors.receive{(ctx,msg)=>
      val n = greetingCounter + 1
      ctx.log.info("Greeting {} for {} ",n, msg.whom)

      println(s" Greeting $n for ${msg.whom}")
      if(n == max){
        Behaviors.stopped
      }else{
        msg.from ! Greet(msg.whom,ctx.self)
        Behaviors.same
      }

    }


  }


  object CustomDispatchersExample {
    object HelloWorldMain {

      final case class SayHello(name: String)

      //#hello-world-main-with-dispatchers
      def apply(): Behavior[SayHello] =
        Behaviors.setup { context =>
          val dispatcherPath = "akka.actor.default-blocking-io-dispatcher"

          val props = DispatcherSelector.fromConfig(dispatcherPath)
          val greeter = context.spawn(HelloWorld(), "greeter", props)

          Behaviors.receiveMessage { message =>
            val replyTo = context.spawn(HelloworldBot(max = 3), message.name)

            greeter ! HelloWorld.Greet(message.name, replyTo)
            Behaviors.same
          }
        }
      //#hello-world-main-with-dispatchers
    }
  }


  def main(args: Array[String]): Unit = {
    ///println(calc.add(calc.add(12, 76), 334))

    val system: ActorSystem[HelloWorldMain.SayHello] =
      ActorSystem(HelloWorldMain(), "hello")

    system tell(HelloWorldMain.SayHello("Mobrtd")  )
    system tell(HelloWorldMain.SayHello("Mukisa")  )

    //system ! HelloWorld.Greet("World"  )
    //system ! HelloWorldMain.SayHello("Akka")


  }

}