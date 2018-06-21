package tpscala_test;


object Printer {
  
  def stringOfOper(o: Operation):String = o match {
    case Plus() => "+"
    case Minus()  => "-"
    case Times()  => "*"
    case Slash()  => "/"
    case Less()  => "<"
    case Greater()  => ">"
    case LessEq()  => "<="
    case GreaterEq()  => ">="
    case Equal()  => "="
    case And()  => "&"
    case Or()  => "|"
  }
    
  def stringOfExp(e: Exp):String = e match {
    case IntCst(i) => String.valueOf(i)
    case Var(v) => v
    case Oper(o, e1, e2) => "(" + stringOfExp(e1) + stringOfOper(o) + stringOfExp(e2) + ")"
  }
    
  def stringOfComm(c: Comm):String = c match {
    case Null () => "null;\n"
    case Assign (v, e) => v + " :=" + stringOfExp(e) + ";\n"
    case Seq (c1, c2) => stringOfComm(c1) + stringOfComm(c2)
    case While (e, c) => "while " + stringOfExp(e) + " loop\n" + stringOfComm(c) + "end loop" + ";\n"
    case IfThen (e, c) => "if " + stringOfExp(e) + " then\n" + stringOfComm(c) + "end if" + ";\n"
    case IfThenElse (e, c1, c2) => 
      "if " + stringOfExp(e) + " then\n" + stringOfComm(c1) + "else" + stringOfComm(c2) + "end if" + ";\n"
    case CommRegion (c, _, _) => stringOfComm(c)
  }
  
  def main(args: Array[String]) : Unit = {
    val c = Parser.parseFile("resources/test1.adb")  
    print(stringOfComm(c))
  }

}