
public class Main {

	public static void main(String[] args) {
		
		double a = 5.2;
		double b = 4.5;
		double x = 2;
		double res;
		
		FonctionAffine fa = new FonctionAffine(a,b);
		
		res = fa.calcule(x);
		
		System.out.println("x=" + x + " " + fa + " res : " + res);
	}

}
