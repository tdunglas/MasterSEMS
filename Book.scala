package tp_comprehension

case class Book(title: String, authors: String*)

case class Queen(x: Int, y: Int) // x, y < N


object obj {
  
  /*
   * EXERCICE 2
   */
    
    type Placement = List[Queen]
    
    def inCheck(q1: Queen, q2: Queen):Boolean = if(q1.x-q2.x == q1.y-q2.y || q1.x-q2.x == q1.y+q2.y) true else false
      
    
    //def isSafe(queen: Queen, p: Placement)

    
    
    
    
  /*
   * EXERCICE 1
   */
  
  val books: List[Book] = List(
    new Book("Principles of Compiler Design", "Aho, Alfred", "Ullman, Jeffrey"),
    new Book("Programming in Modula-2", "Wirth, Niklaus"),
    new Book("Elements of ML Programming", "Ullman, Jeffrey"),
    new Book("The Java Language Specification", "Gosling, James","Joy, Bill", "Steele, Guy", "Bracha, Gilad"),
    new Book("Structure and Interpretation of Computer Programs","Abelson, Harold", "Sussman, Gerald J."))
  
   
    def f1(books: List[Book]) = for(x <- books)yield(x.title)
    
    def f2(books: List[Book]) = for(x <- books if x.title.contains("Program"))yield(x)
    
    def f3(books: List[Book]) = for(x <- books if x.authors.length == 1)yield(x)
    
    def f4(books: List[Book], n: Integer) = for(x <- books if x.authors.length == n)yield(x.title, n)
    
    def f5(books: List[Book]) = {
      var resauthors: List[String] = Nil
      for(x <- books)yield( for(y <- x.authors)yield( resauthors = y::resauthors))
      resauthors.distinct
    }
    
    def f6(books: List[Book], a: String) = for(x <- books if x.authors.contains(a))yield(x.title, a)
    
    def f7(books: List[Book]) = {
      var resauthors: List[String] = Nil
      for(x <- books)yield(for(y <- x.authors if(f6(books, y).length > 1 ))yield(resauthors = y::resauthors))
      resauthors.distinct
    }
        
    def f8(books: List[Book]) = {
      var resauthors: List[String] = Nil
      for(x <- books if x.authors.contains("Ullman, Jeffrey"))(for(y <- x.authors if y != "Ullman, Jeffrey")(resauthors = y::resauthors))
      resauthors.distinct
    }
}