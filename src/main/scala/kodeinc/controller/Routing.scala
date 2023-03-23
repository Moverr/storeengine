package kodeinc.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route}

class Routing {


   def getRoutes = {
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

object  Routing extends Routing{
  def apply(): Route = new Routing().getRoutes
}