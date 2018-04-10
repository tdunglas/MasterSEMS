generic
   type Item is private;
   Max : Natural := 256;

package Queue_RB with SPARK_Mode is

   type ring_buffer is private;

   function Is_Empty(R : ring_buffer) return Boolean;

   function Is_Full(R : ring_buffer) return Boolean;

   procedure Clear(R : in out ring_buffer)
     with Post => Is_Empty(R);

   procedure Add_Last(R : in out ring_buffer; X : in Item)
     with Pre => not Is_Full(R),
     Post => not Is_Empty(R);

   procedure Remove_First(R : in out ring_buffer; X : out Item)
     with Pre => not Is_Empty(R),
     Post => not Is_Full(R);

private

   subtype Index_start is Natural range 0 .. Max -1;
   subtype Index_end is Natural range 0 .. Max -1;
   subtype rb_size is Natural range 0 .. Max -1;
   type Vector is array(Natural range<>) of Item;

   type ring_buffer is
      record
         rb_start : Index_start := 0;
         rb_end : Index_end := 0;
         size : rb_size := 0;
         Vect : Vector(1 .. Max);
      end record;

end Queue_RB;
