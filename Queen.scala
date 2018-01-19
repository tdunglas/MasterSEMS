
case class Queen(x: Int, y: Int){ // x, y 

	type Placement = List[Queen]

	def inCheck(q1: Queen, q2: Queen):Boolean = 
	  if(scala.math.pow(q1.x - q2.x, 2) == scala.math.pow(q1.y - q2.y, 2)) true else false

  def isSafe(queen: Queen, p: Placement):Boolean = {
	    
	    var ret:Boolean = true
	    
	    p.foreach(q => if(inCheck(queen, q)){ ret = false})
	      
	    ret
	  }

	def placeQueens(n: Int, k: Int): List[Placement] = {
	  
	  if(k == 0) return Nil // no queen to place
	  
	  var res:List[Placement] = Nil // for more than one queen
	  for(x <- 1 until n; y <- 1 until k) (

	      for(p <- res) 
	        ( if(isSafe(Queen(x,y), p)) 
	            
	            res = List(Queen(x,y)) :: res
	        )
	      )
	  
	  res
	  
	  /*
	   * if(k ==0) List(List())
	   * else for{ 
	   * 					p <- placeQueens(n, k-1)
	   * 					c <- 1 to n
	   * 					q = Queen(k,c) if isSafe(q,p) 
	   * } yield q :: p 
	   */
	}
	
	def queens(n:Int) = placeQueens(n,n)
	  
}