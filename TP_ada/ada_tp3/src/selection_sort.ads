package selection_sort
with SPARK_Mode
is

   type Vect is array (Natural range <>) of Integer;

   procedure Swap(V: in out Vect; I, J : Natural)
     with
       Pre => V'First < V'Last and I in V'Range and J in V'Range,
     Post => V = V'Old'Update (I => V'Old(J), J => V'Old(I));


   function Min_Index(V: Vect) return Natural
     with
       Pre => V'First <= V'Last,
       Post => Min_Index'Result in V'Range and (for all X in V'Range => (V(Min_Index'Result) <= V(X)));

   procedure SelectionSort(V : in out Vect)
     with
       Pre => V'First < V'Last,
       Post => (for all X in V'First .. V'Last-1 => (V(X) <= V(X+1)));

   end selection_sort;
