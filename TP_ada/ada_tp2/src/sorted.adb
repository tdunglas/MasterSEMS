package body Sorted
with SPARK_Mode
is

   procedure Init (V: out Vect) is
   begin

      for I in V'First .. V'Last loop
         V(I) := 0;
         pragma Loop_Invariant( for all X in V'First .. I => (V(X) = 0));
      end loop;
      pragma Assert( for all X in V'First .. V'Last => (V(X) = 0));
   end Init;

   function Is_Sorted (V: in Vect) return Boolean is
   begin

      for I in V'First .. V'Last - 1 loop
         if(V(I) > V(I + 1)) then
            return False;
         end if;
      end loop;

      return True;
   end Is_Sorted;

   function Min (V: in Vect) return Integer is
   min : Integer := V(V'First);
   begin

      for I in V'First + 1 .. V'Last loop
         if(V(I) < min) then
           min := V(I);
         end if;
      end loop;

      return min;

   end Min;

end Sorted;
