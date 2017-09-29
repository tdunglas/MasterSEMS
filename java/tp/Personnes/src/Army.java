import java.util.ArrayList;

/**
 * les militaires doivent être connus de l'armée
 * il n'y a qu'une seule armée
 *  Armee contienne une liste des officiers, 
 *  une liste des soldats 
 *  et une liste de tous les militaires (officiers et soldats)
 * @author Thomas.D
 *
 */
public class Army {
	
	static private Army laSeuleArmee = new Army();
	private ArrayList<Military> military = new ArrayList<Military>();
	private ArrayList<Officier> officier = new ArrayList<Officier>();
	private ArrayList<Soldier> soldier = new ArrayList<Soldier>();
	private ArrayList<Regiment> regiment = new ArrayList<Regiment>();
	
    private Army(){
    }
	    static public Army getArmee(){
		return laSeuleArmee;
    }
    
	public Army getArmy(){
		return laSeuleArmee;
	}
	    
    public String toString(){
    	return "";
    }

    public void addMilitary(Military m){
    	military.add(m);
    }
    
    public void addOfficier(Officier o){
    	officier.add(o);
    }
    
    public void addSoldier(Soldier s){
    	soldier.add(s);
    }
    
    public void addRegiment(Regiment r){
    	regiment.add(r);
    }
    
    public String showAllMilitary(){
    	String res = "";
    	
    	for(Military m : military){
			res += "Military " + m.toString() + "\n";
		}
    	
    	return res;
    }
    
    public String showAllOfficier(){
    	String res = "";
    	
    	for(Officier o : officier){
			res += "Officier " + o.toString() + "\n";
		}
    	
    	return res;
    }
    
    public String showAllSoldier(){
    	String res = "";
    	
    	for(Soldier s : soldier){
			res += "Soldier " + s.toString() + "\n";
		}
    	
    	return res;
    }

}
