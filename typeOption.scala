

class typeOption {
     
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

}

object o  {

}