generic
   type Item is private;
   Max : Natural := 256;

package Queue_ADT with SPARK_Mode is

   type Queue is private;

   function Is_Empty(Q : Queue) return Boolean;

   function Is_Full(Q : Queue) return Boolean;

   procedure Clear(Q : in out Queue)
     with Post => Is_Empty(Q);

   procedure Add_Last(Q : in out Queue; X : in Item)
     with Pre => not Is_Full(Q),
     Post => not Is_Empty(Q);

   procedure Remove_First(Q : in out Queue; X : out Item)
     with Pre => not Is_Empty(Q),
     Post => not Is_Full(Q);

private

   subtype Index is Natural range 0 .. Max;
   type Vector is array(Natural range<>) of Item;

   type Queue is
      record
         size : Index := 0;
         Vect : Vector(1 .. Index'Last);
      end record;

end Queue_ADT;
