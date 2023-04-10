//package kodeinc
//
//import akka.actor.typed.scaladsl.Behaviors
//import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
//import kodeinc.calculator._
//
//
//object calculator{
//  sealed trait calc
//  final case class add(a:Int,b:Int) extends calc
//  final case class sub(a:Int,b:Int)extends calc
//  final case class multi(a:Int,b:Int)extends calc
//
//  final  case class Message(result:String) extends calc
//  def apply(): Behavior[calc] = Behaviors.receive{(ctx,message) =>
//    message match {
//      case add(a, b) => {
//        val result= a+b
//        println(a+b)
//
//        Behaviors.same
//      }
//
//      case sub(a, b) => {
//        println(a / b)
//        Behaviors.same
//      }
//      case multi(a, b) => {
//        println(a * b)
//        Behaviors.same
//      }
//      case Message(c) => {
//        println(c)
//        Behaviors.same
//      }
//    }
//
//  }
//}
//object MainApp {
//
//  def main(args: Array[String]): Unit = {
//
//      val system = ActorSystem(calculator(), "StoreEngine")
//    //calculator( add(1,2))
//    system tell(new add(12,2))
//    system ! (new sub(12,2))
//    system tell(new multi(12,2))
//
//    // needed for the future flatMap/onComplete in the end
////    implicit val executionContext = system.executionContext
//
////    val route: Route = new Routing().getRoutes
//
//    /*path("/"){
//    get{
//      println("gtyht Day Akka ")
//      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
//    }
//  }*/
//
//
////    //todoo: create  a controllers
////    val bindingFuture = Http().newServerAt("localhost", 9000).bind(route)
////    //.bind(route)
////    println("Blessed Day Akka ")
////
////
////    println(s"Server now online. Please navigate to http://localhost:9000/hello\nPress RETURN to stop...")
////    StdIn.readLine() // let it run until user presses return
////
//
//  }
//
//}
