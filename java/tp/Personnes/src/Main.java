
public class Main {

	public static void main(String[] args) {

		Personne henry = new Personne("slav","henry");
		BadSoldier ryan = new BadSoldier("ryan","bob");
		Officier gustave = new Officier("rodolf","gustave");
		Regiment r1 = new Regiment();
		Regiment r2 = new Regiment();
		
		r1.addOfficier(gustave);
		 
		ryan.addOrder(new Order("do cleaning",OrderStatus.GIVEN,gustave));
		ryan.addOrder(new Order("do washing"));
		ryan.addOrder(new Order("do brushing"));
		ryan.addOrder(new Order("do food gathering"));

		System.out.println(r1);
		//ryan.showOrder();
		

		//System.out.println(Governement.showAllPersonnes());
		
		/*
		System.out.println(Army.getArmee().showAllMilitary());
		System.out.println(Army.getArmee().showAllOfficier());
		System.out.println(Army.getArmee().showAllSoldier());
		*/
	}

}
