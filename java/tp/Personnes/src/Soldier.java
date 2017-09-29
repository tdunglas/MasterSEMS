
/**
 * Chaque objet gardera une mémoire des ordres donnés et reçus.
 * @author Thomas.D
 *
 */
public class Soldier extends Military{
	
	public Soldier(String n, String fn) {
		super(n, fn);
		Army.getArmee().addSoldier(this);
	}
	
	public void addOrder(Order o){
		o.executeOrder();
		super.orders.add(o);
	}
	
	public void died(){
		super.died();
		super.getRegiment().removeSoldier(this);
		Regiment tmp = getRegiment();
		tmp = null;
	}
}
