
import scala.util.Random

/**
 * Thomas Dunglas TP5 scala polymorphisme
 */

class Stack[A](param:List[A]) {
  
  var stack = param
  
  def push(x:A): Stack[A] = new Stack(x :: stack)
  
  def pop: (A, Stack[A]) = (stack.head, new Stack(stack.drop(0)))


}

object heritage {
  
  def f(x:Int): String = x.toString
  def g(x:String): Int = x.toInt 
  
  def switch(x:Nothing) = if(Random.nextBoolean()) f(x) else g(x) // can't be call
  
  var l:List[Int] = List(1,2,3)
  var l2:List[Any] = "hello" :: l
  
  var l3:Array[Int] = Array(1,2,3)
  
  def reverse(xs:List[Any]) = xs.reverse
  //print(reverse(l))
}

