
with Ada.Text_IO, Ada.Integer_Text_IO;
use Ada.Text_IO, Ada.Integer_Text_IO;


with stack_adt;

procedure Main is

   package Integer_stack_adt is
     new stack_adt(Item => Integer, Default => 0);
   use Integer_stack_adt;

   S : stack;
   Y : Integer;

begin

   clear(S);
   push(S, 3);
   push(S, 9);
   pop(S, Y);

   put(Y);

   push(S, 6);
   push(S, 5);

end Main;
