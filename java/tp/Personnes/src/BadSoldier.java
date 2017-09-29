
/**
 * 
 * mauvais soldats qui n'obéissent pas toujours aux ordres, de manière aléatoire
 * @author Thomas.D
 *
 */
public class BadSoldier extends Soldier{

	public BadSoldier(String n, String fn) {
		super(n, fn);
	}
	
	public void addOrder(Order o){
		
		if(Math.random()<0.5){
			o.executeOrder();
		}
		
		super.orders.add(o);
	}

}
