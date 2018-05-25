

object Interpret {
  
  def evalExp(e: Exp, m: Map[String, Int]): Int = e match {
    case IntCst(i) => i
    case Var(v) => m.getOrElse(v,0);
    case Oper(o, e1, e2) =>
      val a = evalExp(e1,m)
      val b = evalExp(e2,m)
      o match {
        case Plus() => a + b
        case Minus()  => a - b
        case Times()  => a * b
        case Slash()  => a /b
        case Less()  => if(a < b) 1 else 0
        case Greater()  => if(a > b) 1 else 0
        case LessEq()  => if(a <= b) 1 else 0
        case GreaterEq()  => if(a >= b) 1 else 0
        case Equal()  => if(a == b) 1 else 0
        case And()  => if(a == 1 && b == 1) 1 else 0
        case Or()  => if(a == 1 || b == 1) 1 else 0
      }
  }
  
  def evalComm(c: Comm, m: Map[String, Int]): Map[String, Int] = c match {
    case Null () => m
    
    case Assign (v, e) => {
      val i = evalExp(e,m)
      (m-v) + (v->i)
    }
    
    case Seq (c1, c2) => {
      val m1 = evalComm(c1,m)
      val m2 = evalComm(c2,m1)
      m2
    }
    
    case While (e, c) => {
      val i = evalExp(e, m)
      if(i == 1){
        val m1 = evalComm(c, m)
        evalComm(While(e,c),m1)
      }
      else
        m
    }
    
    case IfThen (e, c) => {
      val i = evalExp(e,m)
      if(i == 1)
        evalComm(c,m)
      else
        m
    }
    
    case IfThenElse (e, c1, c2) => {
      val i = evalExp(e,m)
      if(i == 1)
        evalComm(c1,m)
      else
        evalComm(c2,m)
    }
    
    case CommRegion (c, _, _) => evalComm(c,m)
  }
  
  def main(args: Array[String]) : Unit = {
    val c = Parser.parseFile("resources/test1.adb")  
    
    
    //print(stringOfComm(c))
  }
  
}