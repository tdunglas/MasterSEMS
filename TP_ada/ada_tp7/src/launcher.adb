
package body  launcher is


procedure test is

   Q : Queue;
   rb : ring_buffer;
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

   Clear(rb);

   end test;

end launcher;
