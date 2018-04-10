with Ada.Text_IO, Ada.Integer_Text_IO;
use Ada.Text_IO, Ada.Integer_Text_IO;

with Queue_ADT;
with Queue_RB;

package launcher with SPARK_Mode is

   package Integer_queue is
     new Queue_ADT(Item => Integer);
   use Integer_queue;

   package Integer_ring is
     new Queue_RB(Item => Integer);
   use Integer_ring;

   procedure test;


end launcher;
