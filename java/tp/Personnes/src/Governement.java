import java.util.ArrayList;

public class Governement {
	
	private static ArrayList<Personne> personnes;
	
	static {
		personnes = new ArrayList<Personne>();
	}
		
	public static void register(Personne p){
		personnes.add(p);
	}
	
	public static void removeDead(Personne p){
		personnes.remove(p);
	}
	
	public static String showAllPersonnes(){
		String res = "";
		
		for(Personne p : personnes){
			if(!p.isAlive()){
				personnes.remove(p);
			}
			else{
				res += "personne " + p.toString() + "\n";
			}
		}
		
		res += "details military :\n";
		res += Army.getArmee().showAllMilitary();
		res += Army.getArmee().showAllOfficier();
		res += Army.getArmee().showAllSoldier();
		
		return res;
	}
}
