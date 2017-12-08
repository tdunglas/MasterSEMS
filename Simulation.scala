

abstract class Simulation {
  
     type Action = () => Unit;
     
     private var time = 0 // le temps courant
     private var agenda: List[(Int, Action)] = List()
     
     private var timeRun = 0;
     
     def afterDelay(delay: Int, a: Action): Unit = { time = time + delay; agenda = (time,a) :: agenda }
     
     def run(): Unit = agenda.foreach( x => { 
          var(t,a) = x; 
          
          println("actual time : " + timeRun);
          println("action time : " + t);
          
          while(timeRun < t){
               Thread.sleep(500)
               println("actual time calculated : " + timeRun);
               timeRun+=1
          }
          
          println("actual time finished : " + timeRun);
          a(); } 
     )
     
}