package tp6

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._

object obj {
      
  def fib(n: Int): Int = if (n <= 1) 1 else fib(n - 1) + fib(n - 2)
  def getResult[A](x : Future[A]): A = Await.result(x, Duration.Inf)
  
  val x1 = Future(fib(45))
  val r1 = getResult(x1)
  val x2 = List(Future(fib(43)), Future(fib(44)), Future(fib(45)))
  val r2 = x2.map(x => getResult(x))
  
  
  // allow using filter,map,flatMap
  def map [A,B](f: A => B, z: List[Future[A]]): List[Future[B]] = z match {
    case Nil => Nil
    case (h::t) => Future(f(getResult(h))) :: map(f,t)
  }
  
  def flatMap [A,B](f: A => Future[B], z: List[Future[A]]): List[Future[B]] = z match {
    case Nil => Nil
    case (h::t) => f(getResult(h)) :: flatMap(f,t)
  }
    
  def filter [A](p: A => Boolean, z: List[Future[A]]): List[Future[Option[A]]] = z match {
    case Nil => Nil
    case (h::t) => if(p(getResult(h))) Future(Option(getResult(h))) :: filter(p,t)  else filter(p,t)
  }
  
  def collect [A](z : List[Future[A]]): Future[List[A]] = z match {
    case Nil => Future(Nil) 
    case (h::t) => Future(getResult(h) :: getResult(collect(t)))
  }
  
}