import org.omg.CORBA.DynAnyPackage.Invalid


class lfiltrage {
  // renvoie la liste retournée. 
     def rev[A](l: List[A]): List[A] = l match {
          case Nil => Nil
          case (h::t) => rev(t) ++ List(h) 
     }
     
     // renvoie la liste obtenue en concatenant les éléments de la liste donnée en argument.
     def concat[A](l: List[List[A]]): List[A] = l match {
          case Nil => Nil
          case (h::t) => h ++ concat(t)
     }
     
     // retourne la première liste et la concatène à la seconde.
     def revAppend[A](l1: List[A], l2: List[A]): List[A] = l1 match {
          case Nil => l2
          case _ => rev(l1) ++ l2
     }
     
     // retourne la première liste et la concatène à la seconde.
     def revAppend_2[A](l1: List[A], l2: List[A]): List[A] = l1 match {
          case Nil => l2
          case (h::t) => revAppend_2(t, h :: l2)
     }
     
     // nth (l,i) renvoie le i-ème élément de la liste l
     def nth[A](l: List[A], i: Int): A = l match {
          case _ => l(i)
     }
     
     // renvoie le dernier élément de la liste (que l’on supposera non vide).     
     def last[A](l: List[A]): A = l match {
          case Nil => throw new IndexOutOfBoundsException
          case (h::t) => if(t==Nil) h else last(t)
     }
     
     // renvoie le dernier élément de la liste (que l’on supposera non vide). // rev/nth     
     def last_2[A](l: List[A]): A = l match {
          case _ => nth(rev(l),0)
     }
     
     // take (l,i)renvoie la liste composée des i premiers éléments de l
     def take[A](l: List[A], i: Int): List[A] = l match {
          case Nil => throw new NullPointerException
          case (h::t) => if(i==0) Nil else h :: take(t ,i-1)
     }
     
     // drop (l,i) renvoie la liste obtenue en enlevant les i premiers éléments de l
     def drop[A](l: List[A], i: Int): List[A] = l match {
          case Nil => throw new NullPointerException
          case (h::t) => rev(take(rev(l),l.length - i))
     }
     
     // renvoie un couple de listes obtenues en séparant chaque coup le de la liste donnée en argument.
     def unzip[A,B](l: List[(A, B)]): (List[A], List[B]) = l match {
          case Nil => (Nil, Nil)
          case((ha,hb) :: t) => val (lha,lhb) = unzip(t); ((ha :: lha),(hb :: lhb)) 
     }
     
     def zip[A, B](l1: List[A],l2: List[B]): List[(A, B)] =
     (l1, l2) match {
          case (Nil, Nil) => Nil
          case (h1 :: t1, h2 :: t2) => (h1, h2) :: zip(t1,t2)
          case _ => throw new IllegalArgumentException("invalid argument")
     }
}

object o2{
     
     val lf = new lfiltrage
     
     // test rev
     val l1 = List(1,2,3)
     val rev_l1 = lf.rev(l1)
     val id_l1 = lf.rev(lf.rev(l1))
     
     // test concat
     val l2 = List(List(1,2),List(3,4),List(5,6))
     val concat_l2 = lf.concat(l2)
     
     // test revAppend
     val l3 = lf.revAppend(id_l1,concat_l2)
     
     // test nth
     val v1 = lf.nth(List('A','B','C'),2)
     
     // test last
     val v2 = lf.last(l3)
     
     // test take
     val v3 = lf.take(List(1,2,3,4,5,6),3)
     
     val t0 = System.nanoTime()
     // test drop
     val v4 = lf.drop(List('a','z','e','r','t','y'),4)
     val t1 = System.nanoTime()
     val t2 = (t1 - t0)/1000 // millisecond
     
     val r= lf.unzip(lf.zip(List(1,2,3),List(4,5,6)))
     
}