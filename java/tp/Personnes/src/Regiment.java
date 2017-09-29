import java.util.ArrayList;

/**
 * 
 * Un régiment est une unité commandée par un officier et comprenant plusieurs soldats
 * @author Thomas.D
 *
 */
public class Regiment {
	
	private static int compteur;
	private int id;
	private Officier officer = null;
	private ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
	
	public Regiment() {
		id = compteur;
		compteur++;
	}
	
	public int getId(){
		return id;
	}
	
	public void addOfficier(Officier o){ // replace current officier
		officer = o;
		o.setRegiment(this);
	}
	
	public void addSoldier(Soldier s){
		soldiers.add(s);
		s.setRegiment(this);
	}
	
	public Officier getOfficier(){
		return officer;
	}
	
	public ArrayList<Soldier> getSoldiers(){
		return soldiers;
	}
	
	public void removeOfficier(){
		
		if(officer != null){
			officer.setRegiment(null);
			officer = null;
		}
	}
	
	public void removeSoldier(Soldier s){
		soldiers.remove(s);
		s.setRegiment(null);
	}
	
	public void transferOfficier(Officier o, Regiment r){
		removeOfficier();
		r.removeOfficier();
		r.addOfficier(o);
	}
	
	public void transferSoldier(Soldier s, Regiment r){
		removeSoldier(s);
		r.addSoldier(s);
	}
	
	public String toString(){
		String res = "Regiment " + id + "\n";

		res += "["; 
		res += "officier : " + officer + "\n"; 
		
		for(int i=0; i< soldiers.size(); i++){
			res += "soldier : " + soldiers.get(i) + "\n"; 
		}
		
		res += "]"; 
		
		return res;
	}
}
