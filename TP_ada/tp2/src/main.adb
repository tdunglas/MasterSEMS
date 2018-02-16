
with Ada.Text_IO, Ada.Integer_Text_IO;
use  Ada.Text_IO, Ada.Integer_Text_IO;

procedure Main with SPARK_Mode is

   procedure Simple (X: in Natural; Y :out Natural)
     with
       Pre => True,
       Post =>Y = X;


   procedure Simple (X: in Natural; Y :out Natural) is
   begin
      Y := 0;
      for I in 1 .. X loop
         pragma Loop_Invariant(I = Y + 1);
         Y := Y + 1;
      end loop;
      --Put(Y);
   end Simple;

   A : Natural := 13;
   B : Natural;

begin
   Simple(A, B);

end Main;
