
/**
 * Chaque objet gardera une mémoire des ordres donnés et reçus.
 * @author Thomas.D
 *
 */
public class Officier extends Military{

	public Officier(String n, String fn) {
		super(n, fn);
		Army.getArmee().addOfficier(this);
	}
	
	public void addOrder(Order o){
		o.giveOrderStatus();
		super.orders.add(o);
	}
	
	public void died(){
		super.died();
		getRegiment().removeOfficier();
		Regiment tmp = getRegiment();
		tmp = null;
	}
}
