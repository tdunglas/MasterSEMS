with Ada.Text_IO, Ada.Integer_Text_IO;
use Ada.Text_IO, Ada.Integer_Text_IO;
with p, Sorted;
use  p, Sorted;

procedure Main with SPARK_Mode is

   A : Natural := 13;
   B : Natural;
   V : Vect := (5,4,2,3,6);
   N : Integer;

begin
   Simple(A, B);
   N := Min(V);
   Init(V);

   Put(N);

end Main;
