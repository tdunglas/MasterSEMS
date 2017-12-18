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
  def map [A,B](f: A => B, z: List[Future[A]]): List[Future[B]] = 
    z.map(x => x.map(f))
  
  def flatMap [A,B](f: A => Future[B], z: List[Future[A]]): List[Future[B]] = 
    z.map(x => x.flatMap(f))
    
  def filter [A](p: A => Boolean, z: List[Future[A]]): List[Future[Option[A]]] = 
    map((a:A) => if(p(a)) Some(a) else None, z)
  
  def collect [A](z : List[Future[A]]): Future[List[A]] = z match {
    case Nil => Future(Nil)
    case (h::t) => h.flatMap( x => collect(t).map(y => x :: y))
  }
  
}