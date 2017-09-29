
public class Main {

	public static void main(String[] args) {

		Personne henry = new Personne("slav","henry");
		Personne jenny = new Personne("Clore","jenny");
		BadSoldier ryan = new BadSoldier("ryan","bob");
		Soldier paul = new BadSoldier("leuck","paul");
		Officier gustave = new Officier("rodolf","gustave");
		
		/*
		Regiment r1 = new Regiment();
		Regiment r2 = new Regiment();
		
		r1.addOfficier(gustave);
		r1.addSoldier(ryan);
		r1.addSoldier(paul);
		 
		ryan.addOrder(new Order("do cleaning",OrderStatus.GIVEN,gustave));
		ryan.addOrder(new Order("do washing"));
		ryan.addOrder(new Order("do brushing"));
		ryan.addOrder(new Order("do food gathering"));

		System.out.println(r1);

		r1.transferOfficier(gustave, r2);
		r1.transferSoldier(paul, r2);
		System.out.println(r2);

		r1.removeSoldier(ryan);
		System.out.println(r1);
		
		System.out.println(ryan);
		*/

		jenny.marriedTo(ryan, true);
		ryan.deivorced();
		paul.died();
		
		//ryan.showOrder();
		

		System.out.println(Governement.showAllPersonnes());
		
		/*
		System.out.println(Army.getArmee().showAllMilitary());
		System.out.println(Army.getArmee().showAllOfficier());
		System.out.println(Army.getArmee().showAllSoldier());
		*/
	}

}
