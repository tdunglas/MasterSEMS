

abstract class CircuitSimulation extends Simulation{
  
     var notDelay: Int = 1
     var andDelay: Int = 3
     var orDelay: Int = 5
     
     def not(input: Wire, output: Wire): Unit = {
          val action = () => output.sig = !input.sig
          
          input >> (() => afterDelay(notDelay, action))
          run()
     }
     
     def and(in1: Wire, in2: Wire, output: Wire): Unit = {
          val action = (() => if(in2.sig && in1.sig) output.sig = true else output.sig = false )
          
          in1 >> (() => afterDelay(andDelay, action))
          in2 >> (() => afterDelay(andDelay, action))
          run()
     }
     
     def or(in1: Wire, in2: Wire, output: Wire): Unit = {
          val action = () => if(in2.sig || in1.sig)  output.sig = true else output.sig = false
          
          in1 >> (() => afterDelay(orDelay, action))
          in2 >> (() => afterDelay(orDelay, action))
          run()
     }
     
     def probe(s: String, w: Wire): Unit = w >> (() => println(s + w.signalValue) )

}
