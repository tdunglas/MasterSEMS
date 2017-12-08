

class MySimulation extends CircuitSimulation{
  
    
     
}

object main {
     
     val ms = new MySimulation
     
     def main(args : Array[String]):Unit = {
          
          val x, y, z, t = new Wire; // x,y entrées, z fil intermédiaire, t sortie
          ms.and(x, y, z)               // z = x & y
          
          ms.not(z, t)                  // t = !z
          
          ms.probe("nand output ", t)    // afficher les changements de valeurs de t
          x.sig = true               // affectation de x
          y.sig = true               // affectation de y
     }
}