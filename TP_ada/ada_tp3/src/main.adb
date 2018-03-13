
with Ada.Text_IO, Ada.Integer_Text_IO;
use Ada.Text_IO, Ada.Integer_Text_IO;

with selection_sort;
use selection_sort;

procedure Main is
V : Vect := (9,5,6,7,2,1);
begin

   SelectionSort(V);

   for x in V'Range loop
      Put(V(x));
   end loop;

end Main;
