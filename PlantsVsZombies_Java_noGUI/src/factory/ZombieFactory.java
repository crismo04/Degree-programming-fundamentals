package factory;

import logic.objets.zombies.*;
import logic.objets.Zombies;


public class ZombieFactory {		
	private static Zombies[] availableZombies = {
			new Zombie(),
			new Caracubo(),	
			new Deportista()
	};
		
	// plantName ya viene reducido a una letra
	public static Zombies getZombie(String zombName){
		Zombies z = null;
		switch(zombName) {
		case "zombie":
		case "z":
			z = new Zombie();  // quizas aqui vaya con parametros
			break;
		case "caracubo":
		case "w":
			z = new Caracubo();
			break;
		case "deportista":
		case "x":
			z = new Deportista();
			break;
		}
		return z;
	}
	
	//lo usará el comando listCommand para mostrar la información
	public static String listOfAvilableZombies() {
		String disponibles = "";
		for(int i = 0; i < availableZombies.length; i++) {
			disponibles +="/n" + availableZombies[i].getName() + ": ";
			disponibles += "Coste->" + availableZombies[i].getCoste() + "suncoins ";
			disponibles += "Daño->" + availableZombies[i].getDanio();
		}
		return disponibles + "\n\n";
	}
}

