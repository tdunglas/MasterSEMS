

with Ada.Text_IO, Ada.Integer_Text_IO;
use  Ada.Text_IO, Ada.Integer_Text_IO;

with Array_IO;
use Array_IO;

procedure Main is
      	N : Integer := 0;
   	S : Integer := 0;
begin

   Put_Line("enter array size ");
   Get(N);

   declare
      V: Vect(1 .. N);
   begin
      Input(V);
      Output(V);
   end;

end Main;
