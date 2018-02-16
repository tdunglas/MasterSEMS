
with Ada.Text_IO, Ada.Integer_Text_IO;
use  Ada.Text_IO, Ada.Integer_Text_IO;

package body Array_IO is


   procedure Input(V: out Vect) is
   begin
      for I in V'Range loop
         Put_Line("write array value ");
         Get(V(I));
      end loop;
   end Input;


   procedure Output(V: out Vect) is begin
      for I in V'Range loop
         Put(V(I));
      end loop;
   end Output;


end Array_IO;
