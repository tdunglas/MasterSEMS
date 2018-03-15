

class typeOption {
     
     // tp 4
	def isDefined[A](opt: Option[A]): Boolean = opt match {
     	case None => false
     	case Some(v) => true
	}
	
	def isEmpty[A](opt: Option[A]): Boolean = opt match {
	     case None => true
     	case Some(v) => false
	}
	
	def filter[A](opt: Option[A], p: (A) => Boolean): Option[A]= opt match {
	     case None => opt
     	case Some(v) => if(!p(v)) None else opt
	}
	
	def map[A,B](opt: Option[A], f: (A) => B): Option[B] = opt match {
	     case None => None
     	case Some(v) => Some(f(v))
	}
	
	def flatMap[A,B](opt: Option[A], f: (A) => Option[B]): Option[B] = opt match {
	     case None => None
     	case Some(v) => f(v)
	}
	
	def get[A](opt: Option[A]): A = opt match {
	     case None => throw new Exception("empty value")
	     case Some(v) => if(!isEmpty(opt)) v else throw new Exception("empty value")
	}
	
	def toList[A](opt: Option[A]): List[A]= opt match {
	     case None => Nil
	     case Some(v) => List(v)
	}
	
	
	// tp 5 exo 1
	def find[A,B](l: List[(A,B)], k: A): Option[B] = l match {
	     case Nil => None
	     case (h::t) => if(h._1==k) Option(h._2) else find(t,k)
	}
	
	def any[A](l: List[Option[A]]): Option[A] = l match {
	     case Nil => None
	     case (h::t) => if(h!=None) h else any(t)
	}
	
	def flatten[A](z: Option[Option[A]]): Option[A] = z match {
	     case None => None 
	     case Some(v) => v
	}
	
	def split[A,B](z: Option[(A,B)]): (Option[A],Option[B]) = z match {
	     case None => (None,None)
	     case Some(v) => (Some(v._1),Some(v._2))
	}
	
	def combine[A,B](x: Option[A], y: Option[B]): Option[(A,B)] = (x,y) match {
	     case (None,None) => None
	     case (None,v) => None
	     case (v,None) => None
	     case (Some(va),Some(vb))=> Some(va,vb)
	}
	
	def extract[A](z: List[Option[A]]): List[A] = z match {
	     case Nil => Nil
	     case (None::t) => extract(t)
	     case (Some(h)::t) => h :: extract(t)
	}
	
	def extractAll[A](z: List[Option[A]]): Option[List[A]] = z match {
	     case Nil => None
	     case (None::t) => None
	     case (Some(h)::t) => extractAll(t) match {
	          case None => None 
	          case Some(v) => Some(h::v)
	     }
	}

	def map[A,B](f: A => B, z: List[Option[A]]): List[Option[B]] = z match {
	     case Nil => Nil
	     case (None::t) => map(f,t)
	     case (Some(h)::t) => Some(f(h)) :: map(f,t)
	}
	
	def flatMap[A,B](f: A => Option[B], z: List[Option[A]]): List[Option[B]] = z match {
	     case Nil => Nil
	     case (None::t) => flatMap(f,t)
	     case (Some(h)::t) => f(h) :: flatMap(f,t)
	}

	def filter[A](p: A => Boolean, z : List[A]): List[Option[A]] = z match {
	     case Nil => Nil
	     case (h::t) => if(p(h)) Some(h) :: filter(p,t) else None :: filter(p,t) 
	}
	
	
	// tp 5 exo 2
	def flatten_2[A](z: Option[Option[A]]): Option[A] = {
	     val Some(t) = z
	     t
	}
	
	def split2[A,B](z: Option[(A,B)]): (Option[A],Option[B]) = {
	     val Some((a,b)) = z
	     (Some(a),Some(b))
	}
	
	def combine_2[A,B](x: Option[A], y: Option[B]): Option[(A,B)] = {
	     val (Some(a), Some(b)) = (x,y)
	     Some(a,b)
	}
	
	def extract_2[A](z: List[Option[A]]): List[A] = z match {
	     case Nil => Nil
	     case (h::t) => val Some(v) = h
	          v :: extract(t)
	}
	
	def extractAll_2[A](z: List[Option[A]]): Option[List[A]] = z match {
	     case Nil => None
	     case (h::t) => if(h == None) None else {
	          val Some(v) = h
	          val Some(v2) = extractAll(t) 
	          Some(v :: v2)
	     }
	}
	
}

object o  {

}