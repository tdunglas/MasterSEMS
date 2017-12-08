

class ComplexCircuitSimulation extends CircuitSimulation{
     
     def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire) = {
          val d,e = new Wire
          
          or(a,b,d)
          and(a,b,c)
          not(c,e)
          and(d,e,s)
     }
     
     def adder(a: Wire, b: Wire, cin: Wire, sum: Wire, cout: Wire) = {
          val s,c1,c2 = new Wire
          
          halfAdder(b,s,sum,c2) 
          halfAdder(a,cin,s,c1) 
          or(c2,c1,cout)
     }

}