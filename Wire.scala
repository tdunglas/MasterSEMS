
class Wire {

     type Action = () => Unit;

     var signalValue: Boolean = false;
     var actions: List[Action] = List();

     def sig: Boolean = signalValue;

     def sig_= (b: Boolean) =
          if (b != signalValue) {
               signalValue = b
               actions.foreach(x => x())
          }

     def >>(a: Action) = { actions = a :: actions; a() }

}

object o {

     val w = new Wire
     w >> (() => println("w devient " + w.sig))
     w.sig = true
     w.sig = false
}