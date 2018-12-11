import java.io.Reader
import java.io.FileReader
import java.io.IOException
import java.util.NoSuchElementException

import Node._
import Parser._

/**
 * Thomas Dunglas SEMS - SCALA
 */
case class Printer ()

object Printer {
  
  def newline: String = {
    "\n"
  }
  
  def string_of_typ(ty: Typ) : String  = {
    ty match {
      case TypInt() =>
        "int"
      case TypBool() =>
        "bool"
      }
  }

  def string_of_env( l : List[(String, Typ)]) : String = l match {
    case Nil => ""
    case ((h1, h2) :: t) => {
      h1 + ": " + string_of_typ(h2) "; "+ string_of_env(t)
    }
  }
  
  def string_exp(exp : Exp) : String = exp match {
    case Var (field1: String) => field1
    case BoolTrue () => "true"   
    case BoolFalse () => "false"   
    case Cst (field1: Int) => ""   
    case Plus (field1: Exp, field2: Exp) => ""
    case Minus (field1: Exp, field2: Exp) => ""
    case Times (field1: Exp, field2: Exp) => ""
    case Mod (field1: Exp, field2: Exp) => ""
    case Div (field1: Exp, field2: Exp) => ""
    case Equal (field1: Exp, field2: Exp) => ""
    case Less (field1: Exp, field2: Exp) => ""
    case Greater (field1: Exp, field2: Exp) => ""
    case LessEq (field1: Exp, field2: Exp) => ""
    case GreaterEq (field1: Exp, field2: Exp) => ""
    case And (field1: Exp, field2: Exp) => ""
    case Or (field1: Exp, field2: Exp) => ""
    case Not (field1: Exp) => ""
    case Imply (field1: Exp, field2: Exp) => ""   
    case IfThenElse (field1: Exp, field2: Exp, field3: Exp) => ""   
    case Pre (field1: String) => ""
    case Arrow (field1: Exp, field2: Exp) => ""   
  }
  
  def string_of_eqns( l: List[Equation]) : String = l match {
    case Nil => ""
    case (h :: t) => {
      var hs = h match {
        case Eqn (f1: String, f2: Exp) => f1 + " = " + string_exp(f2)
        case EqnRegion (f1: Equation, f2: Int, f3: Int) => string_of_eqns(f1)
      }
      
      hs + string_of_eqns(t)
    }
  }
  
  def string_node(node: Node) : String  = {
    ("node " + (node.name + (" (" + (string_of_env(node.inputs) + (") returns (" + (string_of_env(node.outputs) + (")" 
        + (newline + ("var " + (string_of_env(node.locals) 
            + (newline + ("let" 
                + (newline + (string_of_eqns(node.eqns) + ("tel" 
                    + newline)))))))))))))))
  }
  
  def main(args: Array[String]) : Unit  = {
    if (args.length == 0) {
        println("file lus not put in parameter");
        print(string_node(parseFile("stable.lus")));
    }
    else {
    	val filename = args(0);     
    	print(string_of_node(parseFile(filename)));
    }       
  }
  
}
