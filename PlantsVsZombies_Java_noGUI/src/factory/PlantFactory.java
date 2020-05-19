package factory;

import logic.objets.plants.*;
import logic.objets.*;
public class PlantFactory {
	
	private static Plants[] availablePlants = {
			new Sunflower(),
			new Peashooter(),
			new Nut(),
			new Cherrybomb()
	};
	
	public static Plants getPlant(String plantName){
		Plants p = null;
		switch(plantName) {
		case "peashooter":
		case "p":
			p = new Peashooter();    
			break;
		case "sunflower":
		case "s":
			p = new Sunflower();
			break;
		case "nut":
		case "n":
			p = new Nut();
			break;
		case "Cherrybomb":
		case "c":
			p = new Cherrybomb();
			break;
		}
		return p;
	}
	
	//this will be used by the listCommand to display the plants information
	public static String listOfAvilablePlants() {
		String available = "";
		for(int i = 0; i < availablePlants.length; i++) {
			available +="/n" + availablePlants[i].getName() + ": ";
			available += "Price->" + availablePlants[i].getPrice() + "suncoins ";
			available += "Damage->" + availablePlants[i].getDamage();
		}
		return available + "\n\n";
	}

}
