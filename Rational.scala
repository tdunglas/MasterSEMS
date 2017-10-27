

class Rational(val _num:Int,val _den:Int) {
  
     // no rational with den null
     require (_den != 0);
     
     // simplification num
     val num = _num / pgcd(_num,_den);
     
     // simplification num
     val den = _den / pgcd(_num,_den);
     
     override def toString():String = num + "/" + den;
     
     private def pgcd(a: Int, b: Int): Int = 
          if(b==0){
               a
          }
          else{
               pgcd(b, a%b);
          };
          
     
     // opposite
     def unary_-():Rational = new Rational(-num, den);
     
     // inverse
     def reverse(that: Rational):Rational = new Rational(that.den, that.num);
     
     // Operations 
     def +(that: Rational): Rational = new Rational(num * that.den + den * that.num, den * that.den);
     
     def +(that: Int): Rational = if(that == 0) this else new Rational(num + that * den, den * that);
     
     
     def -(that: Rational): Rational = new Rational(num * that.den - den * that.num, den * that.den);
     
     def -(that: Int): Rational = if(that == 0) this else new Rational(num - that * den, den * that);
     
     def *(that: Rational): Rational = new Rational(num * that.num, den * that.den);
     
     def *(that: Int) = if(that == 0) 0 else new Rational(num * that, den);
     
     def /(that: Rational): Rational = this.*(reverse(that));
     
     def /(that: Int): Rational = this./(new Rational(that, 1));
     
     implicit def intToRational(x:Int) = new Rational(x, 1)
     
}

object test{
     val x = new Rational(6,3); 
     
     //val err = new Rational(6,0); 
     
     val t = -x;
     
     val y = t + x;
     
     val one = new Rational(1,1);
     val half = new Rational(1,2);
     val third = new Rational(1,3);
     
     val res_1 = one + third * half;
     val res_2 = (one + third) * half;
     val res_3 = one - one - one;
     val res_4 = half + 1; 
     
}