package kodeinc

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import kodeinc.controller.StoreController

import scala.io.StdIn


object MainApp {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "StoreEngine")


    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext


    val itemRoutes: Route = getRoutes


    val route: Route = {
      pathPrefix("items")(itemRoutes)

    }

    /*path("/"){
    get{
      println("gtyht Day Akka ")
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
    }
  }*/


    //todoo: create  a controllers
    val bindingFuture = Http().newServerAt("localhost", 9000).bind(route)
    //.bind(route)
    println("Blessed Day Akka ")


    println(s"Server now online. Please navigate to http://localhost:9000/hello\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return


  }


  private def getRoutes = {
    val itemRoutes: Route =


      concat(
        path("listing") {
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          }
        }
        ,
        path("show" / Segment) { itemId =>
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
          }
        }
      )
    itemRoutes
  }
}
