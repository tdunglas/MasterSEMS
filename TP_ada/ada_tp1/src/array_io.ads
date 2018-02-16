package Array_IO is

   type Vect is array (Natural range <>) of Integer;

   procedure Input(V: out Vect);

   procedure Output(V: out Vect);

end Array_IO;
