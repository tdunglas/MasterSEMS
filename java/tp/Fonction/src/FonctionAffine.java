
public class FonctionAffine {

	double a;
	double b;
	
	public FonctionAffine(double d1, double d2){
		a = d1;
		b = d2;
	}
	
	public double calcule(double x){
		return a*x+b;
	}
	
	public String toString(){
		return "function affine, f(x) = " + a + "x+" + b;
	}
}
