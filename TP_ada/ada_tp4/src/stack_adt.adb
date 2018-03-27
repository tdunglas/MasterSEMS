package body stack_adt
with SPARK_Mode
is

   function is_Empty(S : stack) return Boolean is
     (S.size = 0);

   function is_Full(S : stack) return Boolean is
     (S.size = Index'Last);

   procedure clear(S : in out stack) is
   begin
      S.size := 0;
   end clear;

   procedure push(S : in out stack; X : in Item) is
   begin
      S.size := S.size + 1;
      S.Vect(S.size) := X;
   end push;

   procedure pop(S : in out stack; X : out Item) is
   begin
      X := S.Vect(S.size);
      S.size := S.size - 1;
   end pop;

end stack_adt;
