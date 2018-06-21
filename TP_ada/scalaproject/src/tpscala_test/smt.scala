package tpscala_test

import scala.collection.mutable.Map
import scala.collection.mutable.Queue

object smt {
  
  def getAllVars(e : Exp): Set[String] = e match {
        case IntCst(i) => Set()
        case Var(v) => Set(v)
        case Oper(o, e1, e2) => getAllVars(e1) ++ getAllVars(e2)
    }

    def getAllVars(l: List[(String, Exp)]): Set[String] = {
        var set : Set[String] = Set()
        for ((v, e) <- l) {
            if( v != "true" && v != "false") {
                set = set ++ Set(v)
            }
            set = set ++ getAllVars(e)
        }
        set
    }

    def smtStringOfExp(e: Exp): String = e match {
        case IntCst(i) => String.valueOf(i)
        case Var(v) => v
        case Oper(o, e1, e2) => "(" + Printer.stringOfOper(o) + " " + smtStringOfExp(e1) + " " + smtStringOfExp(e2) + ")"
    }

    def smtDisplayDeclarations(l: Set[String]): Unit = {
        println("; Variable declarations")
        for (s <- l) {
            println("(declare-fun " + s + " () Int)")
        }
    }

    def smtDisplayConstraints(l: List[(String, Exp)]): Unit = {
        println("; Constraints")
        for ((v, e) <- l) {
            println("(assert (= " + v + " " + smtStringOfExp(e) + "))")
        }
    }

    def smtDisplayAll(l: List[(String, Exp)]): Unit = {
        val s = getAllVars(l)
        smtDisplayDeclarations(s)
        println()
        smtDisplayConstraints(l)
        println()
        println("; Solve\n(check-sat)\n(get-model)")
    }

    def main(args: Array[String]) : Unit  = {
        val c = Parser.parseFile("resources/test1.adb")
        val m = Map("inf" -> 2, "sup" -> 2)
        println(m)

        val ds = Queue(true, false)
        val versions : Map[String, Int] = Map()
        println(ds)
        val l = Interpret.symbolic(c, ds, versions)
        smtDisplayAll(l)
    }
  
}