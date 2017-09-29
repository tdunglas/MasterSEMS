import java.util.ArrayList;

public class Military extends Personne{

	protected ArrayList<Order> orders;
	private Regiment regiment;
	
	public Military(String n, String fn) {
		super(n, fn);
		orders = new ArrayList<Order>();
		Army.getArmee().addMilitary(this);
		regiment = null;
	}
	
	public Regiment getRegiment(){
		return regiment;
	}
	
	/**
	 *  only call with regiment class
	 * @param r : new regiment for soldier
	 */
	public void setRegiment(Regiment r){
		regiment = r;
	}
	
	/**
	 * Les officiers et les soldats auront une méthode d'affichage des ordres pris en compte. 
	 */
	public void showOrder(){
		for(Order o : orders){
			System.out.println("from " + o.getOrderSender() + " : " + o.getOrderContent() + " -  status " + o.getOrderStatus());
		}
	}
	
	public String toString(){
		if(regiment != null)
			return super.toString() + " regiment : " + regiment.getId();
		return super.toString();
	}

}
