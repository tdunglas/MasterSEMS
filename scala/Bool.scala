
/*
 * ----------------------------------------------------------------- EXO 1
 */

sealed abstract class Bool 
case class True() extends Bool
case class False() extends Bool

sealed abstract class IOption 
case class som(x:Int) extends IOption
case class none() extends IOption

sealed abstract class IList{
     def sum():Int = this match {
          case empty() => 0
          case full(i:Int, t:IList) => i + t.sum()
     }
     
     def length():Int = this match {
          case empty() => 0
          case full(i:Int, t:IList) => 1 + t.length()
     }
     
     def map(f:Int=>Int): IList = this match {
          case empty() => empty()
          case full(i:Int, t:IList) => full(f(i), t.map(f))
     }
}

case class empty() extends IList {
     override def sum():Int = 0
     override def length():Int = 0
     override def map(f:Int=>Int): IList = empty()
}

case class full(i:Int, t:IList) extends IList {
     override def sum():Int =  i + t.sum()     
     override def length():Int = 1 + t.length()
     override def map(f:Int=>Int): IList = full(f(i), t.map(f))
}


sealed abstract class List[A]{
     
     def length():Int = this match {
          case nil() => 0
          case cons(i:A, t:List[A]) => 1 + t.length()
     }
     
     def map(f:A=>A): List[A] = this match {
          case nil() => nil()
          case cons(i:A, t:List[A]) => cons(f(i), t.map(f))
     }
}

case class nil[A]() extends List[A] {
     override def length():Int = 0
     override def map(f:A=>A): List[A] = nil()
}

case class cons[A](i:A, t:List[A]) extends List[A] {
     override def length():Int = 1 + t.length()
     override def map(f:A=>A): List[A] = cons(f(i), t.map(f))
}


/*
 * ----------------------------------------------------------------- EXO 2 
 */
/*
sealed abstract class Expr {
     
     def unary_-():Expr     
     def unary_!():Expr   
     def +(e:Expr):Expr
     def -(e:Expr):Expr
     def /(e:Expr):Expr
     def *(e:Expr):Expr
}

case  class expr1(x:Int) extends Expr{
     def unary_-():Expr = expr1(-x)     
     def unary_!():Expr   
     def +(e:Expr):Expr
     def -(e:Expr):Expr
     def /(e:Expr):Expr
     def *(e:Expr):Expr
}

case  class expr2(x:String) extends Expr
*/


/*
 * ----------------------------------------------------------------- object
 */

object ob {

     // return contrary bool
     def not(b:Bool) = b match{
          case True() => False()
          case False() => True()
     }
     
     val v1_true = not(True());
     val v1_false = not(False());
     
     def map(o: IOption, f:Int=>Int): IOption = o match{
          case none() => none()     
          case som(x) => som(f(x))
     }
     
     val v2_ioption1 = map(none(), (x:Int) => 2*x)
     val v2_ioption2 = map(som(8), (x:Int) => 2*x)
     
     
}
