generic
   type Item is private;
   Default : Item;
   Max : Natural := 256;

package stack_adt
with SPARK_Mode
is

   type stack is private;

   function is_Empty(S : stack) return boolean;
   function is_Full(S : stack) return boolean;

   procedure clear(S : in out stack)
     with Post => is_Empty(S);

   procedure push(S : in out stack; X : in Item)
     with Pre => not is_Full(S),
     Post => not is_Empty(S);

   procedure pop(S : in out stack; X : out Item)
     with Pre => not is_Empty(S),
     Post => not is_Full(S);

private

   subtype Index is Natural range 0..256;
   type Vector is array (Natural range<>) of Item;

   type stack is
      record
         size : Index := 0;
         Vect : Vector(1 .. Index'Last);
      end record;

end stack_adt;
