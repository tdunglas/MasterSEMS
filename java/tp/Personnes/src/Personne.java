
/**
 * chaque personne a un nom, un prénom et un numéro de sécu
 * @author Thomas D.
 *
 */
public class Personne {
	
	private String name;
	private String firstname;
	private final int secuNumber;
	
	private Personne conjoint;
	private CoupleStatus cs;
	
	private boolean alive;
	
	public Personne(String n, String fn){
		
		name = n;
		firstname = fn;
		secuNumber = SecuNumber.getSecuNumber();
		
		conjoint = null;
		cs = CoupleStatus.ALONE;
		alive = true;
		
		Governement.register(this);
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void died(){
		alive = false;
	}

	public void resurection(){
		alive = true;
	}
	
	public CoupleStatus getCoupleStatus(){
		return cs;
	}
	
	public void marriedTo(Personne p, boolean firstDemande){
		
		if(firstDemande){
			if((p.getCoupleStatus() == CoupleStatus.ALONE || p.getCoupleStatus() == CoupleStatus.DIVORCED)
					&&((getCoupleStatus() == CoupleStatus.ALONE || getCoupleStatus() == CoupleStatus.DIVORCED))){
				
				conjoint = p;
				cs = CoupleStatus.MARRIED;
				
			}
			
			p.marriedTo(this, false);
		}
		else{
			
			if(getCoupleStatus() == CoupleStatus.ALONE || getCoupleStatus() == CoupleStatus.DIVORCED){
				
				conjoint = p;
				cs = CoupleStatus.MARRIED;
			}
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getFirstName(){
		return firstname;
	}
	
	public void deivorced(){
		if(conjoint != null){
			cs = CoupleStatus.DIVORCED;
			
			if(conjoint.getCoupleStatus() != CoupleStatus.DIVORCED){
				conjoint.deivorced();
			}
		}
	}
	
	public int getSecuNumber(){
		return secuNumber;
	}
	
	public String toString(){
		String res = "name " + name + " firstname " + firstname + " secuNumber " + secuNumber 
				+ " couple situation " + getCoupleStatus();
		
		if(getCoupleStatus() == CoupleStatus.MARRIED 
				|| getCoupleStatus() == CoupleStatus.DIVORCED){
			res += " to [" + conjoint.getName() + " " + conjoint.getFirstName() + "] ";
		}
		
		res += " alive : " + isAlive();
		return res;
	}
}
