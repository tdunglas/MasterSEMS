
package body p is

   procedure Simple (X: in Natural; Y :out Natural) is
   begin
      Y := 0;
      for I in 1 .. X loop
         pragma Loop_Invariant(I = Y + 1);
         Y := Y + 1;
      end loop;

   end Simple;

end p;
