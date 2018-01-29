
import scala.concurrent.Future
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

object main {
  
  def fib(n : Int) : Int = {
    if (n == 1 || n == 2)
        return n

    return fib(n-1) + fib(n-2)
}
  
  def main(args: Array[String]) {
        var v = new tp
        var l = fib(25) 
        var l1 = List(9,8,7,6,5,4,3,2,1,0)
        var l2 = List(97,65,82,41,333)
        
        
        var r1 = v.sortRec(l1)
        println("r1 : " + r1)
        
        var r2 = v.sortPara(l2) andThen {
         case Success(listInt) => {println("r2 : " + listInt)}
         case Failure(exception) => {} 
        }
        
        
        var r3 = v.reduce(l1, 0, (x:Int, y:Int) => x+y)
        println("r3 : " + r3)
        
        var r4 = v.mergeSort(l2)
        println("r4 : " + r4)
        
        var r5 = v.reducePara(l1, 0, (x:Int, y:Int) => x+y)andThen {
         case Success(listInt) => {println("r5 : " + listInt)}
         case Failure(exception) => {} 
        }
        
        var r6 = v.mergeSortPara(l2) andThen {
         case Success(listInt) => { println("r6 : " + listInt) }
          case Failure(exception) => {} 
        }
        
    }
}