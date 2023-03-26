package kodeinc.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route}

import scala.concurrent.ExecutionContext

class Routing( implicit val executionContext: ExecutionContext) {


  case class store(id: Long, name: String, description: String)



  def getRoutes =
     concat(
       pathPrefix("strore")(storeRoute),
       pathPrefix("movers")(storeRoute)
     )





  private def storeRoute =
      concat(
        get{
          path("get" / LongNumber) {   id =>
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Wondes</h1>"))
          }
        }

        ,
        path("show" / Segment) { itemId =>
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Wondes</h1>"))
          }
        }
      )

}

