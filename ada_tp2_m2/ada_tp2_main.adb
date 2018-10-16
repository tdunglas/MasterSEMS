with Text_IO, Ada.Integer_Text_IO, Ada.Real_Time;
use Text_IO, Ada.Integer_Text_IO, Ada.Real_Time;

procedure Main is

   function To_String (T : Time_Span) return String is
   begin
      return (Duration'Image(To_Duration(T)));
   end;

   type Schedule is array (Positive range <>) of Natural;

   T1_Schedule : Schedule(1..6) := (1 => 0, 2 => 200, 3 => 400, 4 => 600, 5 => 800, 6 => 1000); 
   -- period 200

   T2_Schedule : Schedule(1..4) := (1 => 100, 2 => 300, 3 => 700, 4 => 900); 
   -- period 300

   T3_Schedule : Schedule(1..3) := (1 => 250, 2 => 550, 3 => 1050);
   -- period 400

   -- hyper-period 1200 = ppcm(200, 300, 400)

   task type Task_1
     with CPU => 1
   is
      entry Start;
   end;

   task body Task_1 is
      Activation : Time := Clock;
      Next_Activation : Time;
   begin
      accept Start;
      Put_Line ("Task 1 sarting");
      loop
         for I in T1_Schedule'Range loop
            Next_Activation := Activation + Milliseconds(T1_Schedule(I));
            delay until Next_Activation;
            Put_Line ("Task 1 activated " & To_String(Clock - Activation));
            -- run task 1
            delay until Next_Activation + Milliseconds(40);
            Put_Line ("Task 1 done " & To_String(Clock - Activation));
         end loop;
         Activation := Activation + Milliseconds(1200);
      end loop;
   end Task_1;

   task type Task_2
     with CPU => 1
   is
      entry Start;
   end;

   task body Task_2 is
      Activation : Time := Clock;
      Next_Activation : Time;
   begin
      accept Start;
      Put_Line ("Task 2 sarting");
      loop
         for I in T2_Schedule'Range loop
            Next_Activation := Activation + Milliseconds(T2_Schedule(I));
            delay until Next_Activation;
            Put_Line ("Task 2 activated " & To_String(Clock - Activation));
            -- run task 2
            delay until Next_Activation + Milliseconds(100);
            Put_Line ("Task 2 done " & To_String(Clock - Activation));
         end loop;
         Activation := Activation + Milliseconds(1200);
      end loop;
   end Task_2;

   task type Task_3
     with CPU => 1
   is
      entry Start;
   end;

   task body Task_3 is
      Activation : Time := Clock;
      Next_Activation : Time;
   begin
      accept Start;
      Put_Line ("Task 3 sarting");
      loop
         for I in T3_Schedule'Range loop
            Next_Activation := Activation + Milliseconds(T3_Schedule(I));
            delay until Next_Activation;
            Put_Line ("Task 3 activated " & To_String(Clock - Activation));
            -- run task 3
            delay until Next_Activation + Milliseconds(50);
            Put_Line ("Task 3 done " & To_String(Clock - Activation));
         end loop;
         Activation := Activation + Milliseconds(1200);
      end loop;
   end Task_3;

   T1 : Task_1;
   T2 : Task_2;
   T3 : Task_3;
begin
   T1.Start;
   T2.Start;
   T3.Start;
end;
