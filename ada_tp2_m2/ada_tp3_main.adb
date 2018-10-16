pragma Task_Dispatching_Policy(FIFO_Within_Priorities);

with Ada.Text_IO, Ada.Integer_Text_IO, Ada.Real_Time, Ada.Execution_Time;
use Ada.Text_IO, Ada.Integer_Text_IO, Ada.Real_Time, Ada.Execution_Time;


procedure Main is
   
   Start_Time : Time;
   
   function To_String (T : Time_Span) return String is
      S : String := Duration'Image(To_Duration(T));
   begin
      return S(1..6);
   end;
   
   function To_String (T : Time) return String is
      SC : Seconds_Count; 
      TS : Time_Span;
   begin
      Split(T, SC, TS);
      return (Seconds_Count'Image(SC) & "s" & Duration'Image(To_Duration(TS)));
   end;

   function To_String (T : CPU_Time) return String is
      SC : Seconds_Count; 
      TS : Time_Span;
   begin
      Split(T, SC, TS);
      return (Seconds_Count'Image(SC) & "s" & Duration'Image(To_Duration(TS)));
   end;

   
   task type Periodic_Task (Num : Integer; P : Integer; C : Integer; T : Integer) 
   with CPU => 2, Priority => P is
      entry Start;
   end;
   
  
   task body Periodic_Task is
      Local_Time : CPU_Time;
      Global_Time : Time_Span;
      Activation : Time := Clock;
   begin
      accept Start;
      loop
         delay until Activation;
         Local_Time := Clock;
         Global_Time := Clock - Start_Time;
         Put_Line ("Task " & Integer'Image(Num) 
                   & " running: " & To_String(Local_Time) 
                   & " wall clock : " & To_String(Global_Time));
         while Clock - Local_Time < Milliseconds (C) loop 
            null; -- this loop simulates the task activity
         end loop;
         Local_time := Clock;
         Global_Time := Clock - Start_Time;
         Put_Line ("Task " & Integer'Image(Num) 
                   & " ending : " & To_String(Local_Time) 
                   & " wall clock : " & To_String(Global_Time));
         Activation := Activation + Milliseconds (T);
         if Clock > Activation then
            Put("Task " & Integer'Image(Num) & " failed to meet deadline. ");
         end if;
      end loop;
   end Periodic_Task;
   
   T1 : Periodic_Task(1, 3, 39, 200);
   T2 : Periodic_Task(2, 2, 99, 300);
   T3 : Periodic_Task(3, 1, 49, 400);
   begin
      Start_Time := Clock;
      T1.Start;
      T2.Start;
      T3.Start;
   end Main;
