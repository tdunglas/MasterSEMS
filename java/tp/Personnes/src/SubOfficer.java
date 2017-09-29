
/**
 * 
 * sous-officiers qui obéissent aux officiers et donnent des ordres aux soldats. 
 * @author Thomas.D
 *
 */
public class SubOfficer extends Officier{

	public SubOfficer(String n, String fn) {
		super(n, fn);
	}

	public void addOrder(Order o){
		
		if(o.getOrderSender() instanceof Officier){
			o.giveOrderStatus();
			super.orders.add(o);
		}
	}
	
	public void giveOrder(Order o, Soldier s){
		s.addOrder(o);
	}
}
