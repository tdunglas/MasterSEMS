
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
        println("r1 : " + r1)
        
        var r2 = v.sortPara(l2) andThen {
         case Success(listInt) => {println("r2 : " + listInt)}
         case Failure(exception) => {} 
        }
        */
        var timeStart = System.nanoTime()
        var r3 = v.reduce(l1, 0, (x:Int, y:Int) => x+y)
        var timeEnd = System.nanoTime()
        println("r3 (reduce), time : " + (timeEnd-timeStart) + " result : " + r3)
        
        timeStart = System.nanoTime()
        var r5 = v.reducePara(l1, 0, (x:Int, y:Int) => x+y)andThen {
         case Success(listInt) => {
           timeEnd = System.nanoTime()
           println("r5 (reducePara), time : " + (timeEnd-timeStart) + " result : " + listInt)}
         case Failure(exception) => {} 
        }
        
        timeStart = System.nanoTime()
        var r4 = v.mergeSort(l2)
        timeEnd = System.nanoTime()
        println("r4 (merge), time : " + (timeEnd-timeStart) + " result : " + r4)
         
        timeStart = System.nanoTime()
        var r6 = v.mergeSortPara(l2) andThen {
         case Success(listInt) => { 
           timeEnd = System.nanoTime()
           println("r6 (mergePara), time : " + (timeEnd-timeStart) + " result : " + listInt) }
          case Failure(exception) => {} 
        }
        
        
        /*
         * 
         * trace perform
         * 
              r3 (reduce), time : 5836017 result : 45
              r5 (reducePara), time : 490087 result : 45
              r4 (merge), time : 4181504 result : List(41, 65, 82, 97, 333)
              r6 (mergePara), time : 1997269 result : List(41, 65, 82, 97, 333)
         */
    }
}
