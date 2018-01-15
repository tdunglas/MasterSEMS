
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
    case (h :: t) => reduce(t, f(z, h), f)
  }

  def mergeSort(l: List[Int]): List[Int] = l match {
    case Nil => Nil
    case (h :: t) => reduce(helper(t), List(h),
      (x: List[Int], y: List[Int]) => {
        var (hx :: tx) = x
        var (hy :: ty) = y

        if (hx < hy) x ++ y else y ++ x
      })
  }
  
  def helper[A](l: List[A]): List[List[A]] = l match {
    case Nil      => List(Nil)
    case (h :: t) => List(List(h)) ++ helper(t)
  }
  
  def reducePara[A](l : List[A], z : A, f : (A, A) => A): Future[A]

}