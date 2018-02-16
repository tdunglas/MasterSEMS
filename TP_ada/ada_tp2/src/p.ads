
package p is

   procedure Simple (X: in Natural; Y :out Natural)
     with
       Pre => True,
       Post =>Y = X;

end p;
