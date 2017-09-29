
public class Order {
	
	private String content;
	private OrderStatus status;
	private Military sender;
	
	public Order(String s, OrderStatus st, Military m){
		content = s;
		status = st;
		sender = m;
	}
	
	public Order(String s){
		content = s;
		status = OrderStatus.GIVEN;
		sender = null;
	}
	
	public void executeOrder(){
		status = OrderStatus.EXECUTED;
	}
	
	public void giveOrderStatus(){
		status = OrderStatus.GIVEN;
	}
	
	public String getOrderContent(){
		return content;
	}
	
	public OrderStatus getOrderStatus(){
		return status;
	}
	
	public Military getOrderSender(){
		return sender;
	}
	
}
