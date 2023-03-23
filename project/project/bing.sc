import scala.annotation.tailrec

def sum(ints:List[Int]):Int={
  ints match {
    case ::(head, tl) => {
      println(head)
      head + sum(tl)
    }
    case Nil => 0
  }
}

val x:List[Int] = List(1,2,3,4,5,6)
val resp=sum(x)
println(x)
