
package Sorted
with SPARK_Mode
is

   type Vect is array (Natural range<>) of Integer;

   procedure Init (V: out Vect)
     with
       Pre => V'First <= V'Last,
       Post => (for all X in V'Range => (V(X) = 0));

   function Is_Sorted (V: in Vect) return Boolean
     with
       Pre => V'First < V'Last,
       Post => (Is_Sorted'Result = (for all X in V'First + 1 .. V'Last => (V(X) >= V(X - 1))));

   function Min (V: in Vect) return Integer
     with
       Pre => V'First < V'Last,
       Post => (for all X in V'Range => (Min'Result <= V(X)));

end Sorted;

