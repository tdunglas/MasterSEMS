import java.io.Reader
import java.io.FileReader
import java.io.IOException
import java.util.NoSuchElementException

import Node._
import Parser._

/**
 * Thomas Dunglas SEMS - SCALA
 */
case class PrinterAda ()

object PrinterAda {
  
  def newline: String = {
    "\n"
  }
  
  def string_of_typ(ty: Typ) : String  = {
    ty match {
      case TypInt() =>
        "Integer"
      case TypBool() =>
        "Boolean"
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
    case Pre (field1: String) => " ( pre_" + field1 + " ) " 
    case Arrow (field1: Exp, field2: Exp) => " ( if init0 " + " then " +  string_exp(field1) + " else " + string_exp(field2) + " ) "    
  }
  
  def string_of_eqns( l: List[Equation]) : String = l match {
    case Nil => ""
    case (h :: t) => {
      var hs = h match {
        case Eqn (f1: String, f2: Exp) => {
          f1 + " := " + string_exp(f2) + ";" + newline
        }
        case EqnRegion (f1, f2, f3) => string_of_eqns(List(f1))
      }

      hs  + string_of_eqns(t)
    }
  }
  
  def string_of_eqns_pre( l: List[Equation]) : String = l match {
    case Nil => ""
    case (h :: t) => {
      var hs = h match {
        case Eqn (f1: String, f2: Exp) => "pre_" + f1 + " := " + f1 + ";" + newline
        case EqnRegion (f1, f2, f3) => string_of_eqns_pre(List(f1))
      }

      hs  + string_of_eqns_pre(t)
    }
  }
  
  def string_of_eqns_init() : String = {
    "init_0 := false;" + newline
  }
  
  def string_of_vars_init() : String = {
    "init_0: Boolean := true;" + newline
  }
  
  def string_of_vars_local(l: List[(String, Typ)]) : String = l match {
    case Nil => ""
    case (h::t) => { 
      var (st, ty) = h
      var res = st + ": " + string_of_typ(ty) + ";" + newline + "pre_" + st + ": " + string_of_typ(ty) + ";" + newline + string_of_vars_local(t)
      res
    }
  }
  
  def string_of_vars_output_init(l: List[(String, Typ)]) : String = l match {
    case Nil => ""
    case (h :: t) => { 
      var (st, ty) = h
      var res = "pre_" + st + ": " + string_of_typ(ty) + ";" + newline + string_of_vars_output(t)
      res
    }
  }
  
  def string_of_vars_output(l: List[(String, Typ)]) : String = l match {
    case Nil => ""
    case (h :: t) => {       
      var (st, ty) = h
      var res = "pre_" + st + ": out " + string_of_typ(ty)
      t match {
        case Nil => res
        case (ht :: tt) => res + ";" + newline + string_of_vars_output(t)
      }        
    }
  }
  
  def get_vars_string(exp : Exp) : List[(String)] = exp match {
    case Var (field1: String) => List(field1)
    case BoolTrue () => List[(String)]()
    case BoolFalse () => List[(String)]()
    case Cst (field1: Int) => List[(String)]() 
    case Plus (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case Minus (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case Times (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case Mod (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case Div (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case Equal (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case Less (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case Greater (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case LessEq (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case GreaterEq (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case And (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case Or (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
    case Not (field1: Exp) => List(" ( " +  " not " + string_exp(field1) + " ) ")
    case IfThenElse (field1: Exp, field2: Exp, field3: Exp) => get_vars_string(field1) ++ get_vars_string(field2) ++ get_vars_string(field3) 
    case Pre (field1: String) => List(" ( pre_" + field1 + " ) ") 
    case Arrow (field1: Exp, field2: Exp) => get_vars_string(field1) ++ get_vars_string(field2)
}
  
  def addEqn(eq: Equation, 
      names: List[String], 
      ordered: List[(Equation, List[String])]) : List[(Equation, List[String])] = ordered match {
        case Nil => List((eq, names))
        case ((e, n)::t) => {
          if(names.forall(x => !n.contains(x))) (eq,names) :: ordered else (e, n) :: addEqn(eq, names, t)
        }
  }
  
  def order_eqs(l: List[Equation], 
      ordered: List[(Equation, List[String])]) : List[Equation] = l match {
    
    case Nil => ordered.flatMap(x => List(x._1)); 
    case (h :: t) => {
      var names = List[String]()
      
      h match {
        case Eqn (field1: String, field2: Exp) => field1 :: get_vars_string(field2) :: names
        case EqnRegion (field1: Equation, field2: Int, field3: Int) => order_eqs(field1 :: t, ordered)
      }
      
      order_eqs(t, addEqn(h, names, ordered))
    }
  }
  
  def order_equations(l: List[Equation], inputs: List[(String, Typ)]) : List[Equation] = l match {
    case Nil => Nil
    case (h :: t) => {
      h match {
        case Eqn (f1: String, f2: Exp) => f2 match {
          case Pre (field1: String) => h :: order_equations(t, inputs)
          case Var (name: String) => {
            var iname = inputs.flatMap(x => List(x._1)); 
            if(iname.contains(name)) h :: order_equations(t, inputs) else order_equations(t :+ h, inputs)
          }
          case _ => {
            order_equations(t, inputs) :+ h
          }
        }
        case EqnRegion (f1, f2, f3) => order_equations(f1 :: t, inputs)
      }        
    }
  }
  
  def string_node(node: Node) : String  = {
    //var ordered_eqns = order_equations(node.eqns, node.inputs) 
    var ordered_eqns = order_eqs(node.eqns, Nil) 
    
    ("procedure " + node.name + " is "
        + newline + string_of_vars_init()
        + newline + string_of_vars_local(node.locals)
        + newline + string_of_vars_output_init(node.outputs)
        + newline + "procedure step"
        + newline + " ( " + string_of_env(node.inputs) + string_of_vars_output(node.outputs) + " ) is "
        + newline + "begin"        
        + newline + string_of_eqns(ordered_eqns)  
        + newline + string_of_eqns_pre(ordered_eqns)  
        + newline + string_of_eqns_init()  
        + newline + "end;"
        + newline + "begin"
        + newline + "null;"
        + newline + "end " + node.name + ";"
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