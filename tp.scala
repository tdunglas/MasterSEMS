
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * tri fusion parallele
 */
class tp {

  def split[A](l: List[A], n: Int): (List[A], List[A]) = (l.take(n), l.drop(n))

  def merge(l1: List[Int], l2: List[Int]): List[Int] = {

    if (l1 == Nil) return l2
    if (l2 == Nil) return l1

    var (h1 :: t1) = l1
    var (h2 :: t2) = l2

    if (h1 < h2) h1 :: merge(t1, l2) else h2 :: merge(l1, t2)
  }

  def sortRec(l: List[Int]): List[Int] = {
    if (l.size <= 1) {
      return l
    }

    val (l1, l2) = l.splitAt(l.size / 2)

    val r1 = sortRec(l1);
    val r2 = sortRec(l2);

    merge(r1, r2)

  }

  def sortPara(l: List[Int]): Future[List[Int]] = { 

    if (l.size <= 1) {
      return Future(l)
    }

    val (l1, l2) = l.splitAt(l.size / 2)

    val f1 = Future(sortRec(l1))
    val f2 = Future(sortRec(l2))

    for (sl1 <- f1; sl2 <- f2) yield (merge(sl1, sl2))

  }

  def reduce[A](l: List[A], z: A, f: (A, A) => A): A = l match {
    case Nil      => z
    case (h :: t) => {
      
      if(l.size > 2){
        var (left, right) = split(l, l.size/2)
        reduce(left, z, f)
        reduce(right, z, f)
      }
      
      fib(20) // to add time for better comparaison
      
      f(h, reduce(t,z,f)) 
      
    }    
  }

  def mergeSort(l: List[Int]): List[Int] = l match {
    case Nil => Nil
    case (h :: t) => {
        reduce(helper(l), Nil, (x: List[Int], y: List[Int]) => {
          sortRec(x ++ y)
        })
    }
  }
  
  def helper[A](l: List[A]): List[List[A]] = l match {
    case Nil      => List(Nil)
    case (h :: t) => List(List(h)) ++ helper(t)
  }
  
  def reducePara[A](l : List[A], z : A, f : (A, A) => A): Future[A] = {
      
      if (l.size <= 1) {
        var (h :: t) = l 
        return Future(h)
      }

      var (left, right) = split(l, l.length/2)
      val f1 = reducePara(left, z, f)
      val f2 = reducePara(right, z, f)
      
      fib(20) // to add time for better comparaison
      
      for(x <- f1; y <- f2) yield( f(x, y))
          
    }
  
  def mergeSortPara(l : List[Int]): Future[List[Int]] = l match {
    case Nil => Future(Nil)
    case (h :: t) => {
        reducePara(helper(l), Nil, (x: List[Int], y: List[Int]) => {
          sortRec(x ++ y)
        })
    }
  }
  
    def fib(n : Int) : Int = {
    if (n == 1 || n == 2)
        return n

    return fib(n-1) + fib(n-2)
  }

}
