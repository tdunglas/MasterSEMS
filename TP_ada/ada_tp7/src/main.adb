with Ada.Text_IO, Ada.Integer_Text_IO;
use Ada.Text_IO, Ada.Integer_Text_IO;

with Queue_ADT;

procedure Main is

   package Integer_queue is
     new Queue_ADT(Item => Integer);
   use Integer_queue;

   Q : Queue;
begin

   Clear(Q);

   if(Is_Empty(Q)) then
      Put_Line("Q is empty");
   end if;

   if(not Is_Full(Q)) then
      Put_Line("Q is not full");
   end if;

   Add_Last(Q, 12);

   if(not Is_Empty(Q)) then
      Put_Line("Q is not empty");
   end if;

end Main;
