package tpscala_test;

import scala.collection.mutable.Map
import scala.collection.mutable.Queue

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
  
  def traceComm(c: Comm, m: Map[String, Int]): List[Boolean] = c match {
    case Null() => List()

    case Assign(v, e) => {
      val i = evalExp(e, m)
      m.put(v, i)
      List()
    }

    case Seq(c1, c2) => {
      val p1 = traceComm(c1, m)
      val p2 = traceComm(c2, m)
      p1 ++ p2
    }

    case While(e, c1) => {
      var p : List[Boolean] = List()
      while (evalExp(e,m) == 1) {
        p = p ++ (true :: traceComm(c1, m))
      }
      p ++ List(false)
    }
    
    case IfThen(e, c) => {
      if (evalExp(e, m) == 1) {
        true :: traceComm(c, m)
      } else {
        List(false)
      }
    }

    case IfThenElse(e, c1, c2) => {
      if (evalExp(e, m) == 1) {
        true :: traceComm(c1, m)
      } else {
        false :: traceComm(c2, m)
      }
    }
    
    case CommRegion(c, _, _) => traceComm(c, m)
  }
  
  def numExp(e : Exp, versions : Map[String, Int]): Exp = e match {
    case IntCst(i) => IntCst(i)
    case Var(v) =>
      val k = versions.getOrElse(v, 0)
      if (k > 0)
        Var(v + "_" + k)
      else
        Var(v)
    case Oper(o, e1, e2) => Oper(o, numExp(e1, versions), numExp(e2, versions))
  }
  
  def symbolic(c: Comm, ds: Queue[Boolean], versions: Map[String, Int]): List[(String, Exp)] = c match {
    case Null() => List()

    case Assign(v, e) => 
      val k : Int = 1 + versions.getOrElse(v, 0)
      val l = List((v + "_" + k, numExp(e, versions)))
      versions.put(v, k)
      l

    case Seq(c1, c2) => {
      val p1 = symbolic(c1, ds, versions)
      val p2 = symbolic(c2, ds, versions)
      p1 ++ p2
    }

    case While(e, c1) => {
      var p:List[(String, Exp)] = List()
      while(next(ds)) {
        p = p ++ List(("true", numExp(e, versions)))
        p = p ++ symbolic(c1, ds, versions)
      }
      p = p ++ List(("false", numExp(e, versions)))
      p
    }
    case IfThen(e, c) => {
      if (next(ds)) {
        ("true", numExp(e, versions)) :: symbolic(c, ds, versions)
      } else {
        List(("false", numExp(e, versions)))
      }
    }

    case IfThenElse(e, c1, c2) => {
      if (next(ds)) {
        ("true", numExp(e, versions)) :: symbolic(c1, ds, versions)
      } else {
        ("false", numExp(e, versions)) :: symbolic(c2, ds, versions)
      }
    }
    
    case CommRegion(c, _, _) => symbolic(c, ds, versions)
  }
  
  def next(ds : Queue[Boolean]) : Boolean = {
    if (ds.isEmpty)
      false
    else
      ds.dequeue()
  }
  
  def main(args: Array[String]) : Unit  = {
    val c = Parser.parseFile("resources/test1.adb")
    val m = Map("inf" -> 2, "sup" -> 1)
    println(m)
    val p = traceComm(c, m)
    println(m)
    println(p)

    val ds = Queue(true, true, true, true, false)
    val versions : Map[String, Int] = Map()
    println(ds)
    val l = symbolic(c, ds, versions)
    println(l.map({ case (v, e) => "\n" + v + " = " + Printer.stringOfExp(e) }))
  }
  
}