package factory;

import logic.objets.plantas.*;
import logic.objets.*;
public class PlantFactory {
	
	private static Plantas[] availablePlants = {
			new Sunflower(),
			new Peashooter(),
			new Nuez(),
			new Cherrybomb()
	};
	
	public static Plantas getPlant(String plantName){
		Plantas p = null;
		switch(plantName) {
		case "peashooter":
		case "p":
			p = new Peashooter();    
			break;
		case "sunflower":
		case "s":
			p = new Sunflower();
			break;
		case "nuez":
		case "n":
			p = new Nuez();
			break;
		case "Cherrybomb":
		case "c":
			p = new Cherrybomb();
			break;
		}
		return p;
	}
	
	//lo usará el comando listCommand para mostrar la información
	public static String listOfAvilablePlants() {
		String disponibles = "";
		for(int i = 0; i < availablePlants.length; i++) {
			disponibles +="/n" + availablePlants[i].getName() + ": ";
			disponibles += "Coste->" + availablePlants[i].getCoste() + "suncoins ";
			disponibles += "Daño->" + availablePlants[i].getDanio();
		}
		return disponibles + "\n\n";
	}

}