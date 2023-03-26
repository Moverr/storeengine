package kodeinc.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route}
import spray.json.DefaultJsonProtocol.{LongJsonFormat, StringJsonFormat, jsonFormat2, jsonFormat3}
import spray.json.RootJsonFormat

import scala.concurrent.impl.Promise
import scala.concurrent.{ExecutionContext, Future}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.Done
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes

import scala.collection.immutable.Nil.:::
// for JSON serialization/deserialization following dependency is required:
// "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7"
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.io.StdIn

import scala.concurrent.ExecutionContext
import scala.concurrent.Future



class Routing( implicit val executionContext: ExecutionContext) {

  case class Store(id: Long, name: String, description: String)

  implicit val itemFormat: RootJsonFormat[Store] = jsonFormat3(Store.apply)
  //implicit val orderFormat: RootJsonFormat[Order] = jsonFormat1(Order.apply)
  var stores:List[Store] = Nil

  def fetchStore(id:Long):Future[Option[Store]] = Future{
    stores.find(x=>x.id==id)
  }
  def  create(store: Store):Future[Done]={
    //  use appended :: append item to list
      stores =  stores.appended(store)
     Future[Done]

  }

  def addStores(_stores: List[Store]): Future[Done] = {
    // use appendedall ::: list to list
    stores = _stores.appendedAll(stores)
    Future[Done]

  }

  def getRoutes =
     concat(
       pathPrefix("strore")(storeRoute),
       pathPrefix("movers")(storeRoute)
     )





  private def storeRoute =
      concat(
        get{
          path("get" / LongNumber) {   id =>

            val  response:Future[Option[Store]] =fetchStore(id)
            onSuccess(response) {
              case Some(value) => complete(value)
              case None => complete(StatusCodes.NotFound)
            }



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

