import java.io.Reader
import java.io.FileReader
import java.io.IOException
import java.util.NoSuchElementException

import Node._
import Parser._

/**
 * Thomas Dunglas SEMS - SCALA
 */
case class PrinterLustre ()

object PrinterLustre {
  
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
      h1 + ": " + string_of_typ(h2) + "; " + string_of_env(t)
    }
  }
  
  def string_exp(exp : Exp) : String = exp match {
    case Var (field1: String) => field1
    case BoolTrue () => "true"   
    case BoolFalse () => "false"   
    case Cst (field1: Int) => String.valueOf(field1)  
    case Plus (field1: Exp, field2: Exp) => " ( " + string_exp(field1) + " + " + string_exp(field2) + " ) "
    case Minus (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " - " + string_exp(field2) + " ) "
    case Times (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " * " + string_exp(field2) + " ) "
    case Mod (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " % " + string_exp(field2) + " ) "
    case Div (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " / " + string_exp(field2) + " ) "
    case Equal (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " == " + string_exp(field2) + " ) "
    case Less (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " < " + string_exp(field2) + " ) "
    case Greater (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " > " + string_exp(field2) + " ) "
    case LessEq (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " <= " + string_exp(field2) + " ) "
    case GreaterEq (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " >= " + string_exp(field2) + " ) "
    case And (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " and " + string_exp(field2) + " ) "
    case Or (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " or " + string_exp(field2) + " ) "
    case Not (field1: Exp) => " ( " +  " not " + string_exp(field1) + " ) "
    case Imply (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " => " + string_exp(field2) + " ) "
    case IfThenElse (field1: Exp, field2: Exp, field3: Exp) => " ( " +  "if " + string_exp(field1) + " then " + string_exp(field2) + " else " + string_exp(field3) + " ) "
    case Pre (field1: String) => " ( pre " + field1 + " ) " 
    case Arrow (field1: Exp, field2: Exp) => " ( " +  string_exp(field1) + " -> " + string_exp(field2) + " ) "    
  }
  
  def string_of_eqns( l: List[Equation]) : String = l match {
    case Nil => ""
    case (h :: t) => {
      var hs = h match {
        case Eqn (f1: String, f2: Exp) => f1 + " = " + string_exp(f2) + ";" + newline
        case EqnRegion (f1, f2, f3) => string_of_eqns(List(f1))
      }

      hs  + string_of_eqns(t)
    }
  }
  
  def string_node(node: Node) : String  = {
    ("node " + node.name + " (" + string_of_env(node.inputs) + ") returns (" + string_of_env(node.outputs) + ");" 
        + newline + "var " + string_of_env(node.locals) 
        + newline + "let" 
        + newline + string_of_eqns(node.eqns)  
        + newline + ("tel" )
    )
  }
  
  def main(args: Array[String]) : Unit  = {
    if (args.length == 0) {
        println("file lus not put in parameter");
        print(string_node(parseFile("res/stable.lus")));
    }
    else {
    	val filename = args(0);     
    	print(string_node(parseFile(filename)));
    }       
  }
  
}