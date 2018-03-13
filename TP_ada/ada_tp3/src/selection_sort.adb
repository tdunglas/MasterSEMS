package body selection_sort
with SPARK_Mode
is

   procedure Swap(V: in out Vect; I, J : Natural) is
      tmp : Integer;
   begin
      if(I in V'Range and J in V'Range) then

         tmp := V(J);
         V(J) := V(I);
         V(I) := tmp;

      end if;

   end Swap;

   function Min_Index(V: Vect) return Natural is
      min : Natural range V'Range := V'First;
   begin

      for X in V'Range loop
         if(V(min) > V(X)) then
            min := X;
         end if;
         pragma Loop_Invariant(V(min) <= V(X));
         pragma Loop_Invariant(for all J in V'First .. X => (V(min) <= V(J)));
      end loop;

      return min;

   end Min_Index;

   procedure SelectionSort(V : in out Vect) is
      M: Natural;
   begin
      for I in V'Range loop
         M := Min_Index(V(I .. V'Last));
         Swap(V, M, I);
         pragma Loop_Invariant(for all x in V'First +1 .. I => (V(x) >= V(x-1)));
         pragma Loop_Invariant(for all x in I .. V'Last => (V(x) >= V(I)));
      end loop;
   end SelectionSort;

end selection_sort;

