package body Queue_RB with SPARK_Mode is

   function Is_Empty(R : ring_buffer) return Boolean is
      (R.rb_start = R.rb_end);

   function Is_Full(R : ring_buffer) return Boolean is
     (R.size = Max);

   procedure Clear(R : in out ring_buffer) is
   begin
      R.size := 0;
      R.rb_start := 0;
      R.rb_end := 0;
   end Clear;

   procedure Add_Last(R : in out ring_buffer; X : in Item) is
   begin
      R.size := R.size + 1;
      R.rb_end := (R.rb_end + 1) mod Max;
      R.Vect(R.rb_end) := X;
   end Add_Last;

   procedure Remove_First(R : in out ring_buffer; X : out Item) is
   begin

      X := R.Vect(R.rb_start);
      R.rb_start := (R.rb_start + 1) mod Max;
      R.size := R.size - 1;

   end Remove_First;


end Queue_RB;
