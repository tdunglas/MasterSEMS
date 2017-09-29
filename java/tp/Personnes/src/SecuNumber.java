
/**
 * deux personnes différentes n'auront pas le même numéro. 
 * deux objets java différents n'auront pas le même numéro. 
 * @author Thomas D.
 *
 */
public class SecuNumber {
	
	private static int number = 0;
	
	public static int getSecuNumber(){
		return number++;
	}
}
