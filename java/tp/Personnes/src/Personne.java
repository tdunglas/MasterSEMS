
/**
 * chaque personne a un nom, un prénom et un numéro de sécu
 * @author Thomas D.
 *
 */
public class Personne {
	
	private String name;
	private String firstname;
	private final int secuNumber;
	
	public Personne(String n, String fn){
		
		name = n;
		firstname = fn;
		secuNumber = SecuNumber.getSecuNumber();
		
		Governement.register(this);
	}
	/*
	public String getName(){
		return name;
	}
	
	public void setName(String s){
		name = s;
	}
	
	public String getFirstName(){
		return firstname;
	}
	
	public void setFirstName(String s){
		firstname = s;
	}
	*/
	public int getSecuNumber(){
		return secuNumber;
	}
	
	public String toString(){
		return "name " + name + " firstname " + firstname + " secuNumber " + secuNumber; 
	}
}
