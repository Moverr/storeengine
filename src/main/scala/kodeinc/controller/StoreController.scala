package kodeinc.controller

import scala.concurrent.{ExecutionContext, Future}


public class StoreController( implicit val executionContext: ExecutionContext) {

  case class Store(id:Long,name:String,description:String)

  var stores:List[Store] = Nil

  def get(id:Long): Future[Option[Store]] = Future{
      stores.find(x=>x.id == id)

  }

}








