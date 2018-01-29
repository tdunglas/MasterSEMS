
import scala.concurrent.Future
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

object main {
  
  def main(args: Array[String]) {
        var v = new tp
        var l1 = List(9,8,7,6,5,4,3,2,1,0)
        var l2 = List(97,65,82,41,333)
        
        /*
        var r1 = v.sortRec(l1)
        println(r1)
        
        var r2 = v.sortPara(l2) andThen {
         case Success(listInt) => {}
          case Failure(exception) => {} 
        }
        println(r2)
        
        var r3 = v.reduce(l1, 0, (x:Int, y:Int) => x+y)
        println(r3)
        
        var r4 = v.mergeSort(l2)
        println(r4)
        
        var r5 = v.reducePara(l1, 0, (x:Int, y:Int) => x+y)
        println(r5)
        */
        var r6 = v.mergeSortPara(l2) andThen {
         case Success(listInt) => {}
          case Failure(exception) => {} 
        }
        println(r6)
    }
}