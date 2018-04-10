package body Queue_ADT with SPARK_Mode is

   function Is_Empty(Q : Queue) return Boolean is
      (Q.size = 0);

   function Is_Full(Q : Queue) return Boolean is
      (Q.size = Index'Last);

   procedure Clear(Q : in out Queue) is
   begin
      Q.size := 0;
   end Clear;

   procedure Add_Last(Q : in out Queue; X : in Item) is
   begin
      Q.size := Q.size + 1;
      Q.Vect(Q.size) := X;
   end Add_Last;

   procedure Remove_First(Q : in out Queue; X : out Item) is
   begin
      X := Q.Vect(Q.Vect'First);

      for I in Q.Vect'First .. (Q.size - 1) loop

         Q.Vect(I) := Q.Vect(I + 1);

      end loop;

      Q.size := Q.size - 1;

   end Remove_First;


end Queue_ADT;
