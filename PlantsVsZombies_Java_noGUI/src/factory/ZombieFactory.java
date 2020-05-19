package factory;

import logic.objets.zombies.*;
import logic.objets.Zombies;


public class ZombieFactory {		
	private static Zombies[] availableZombies = {
			new Zombie(),
			new BucketFace(),	
			new SportZomb()
	};
		
	//zombName is already reduced to one letter
	public static Zombies getZombie(String zombName){
		Zombies z = null;
		switch(zombName) {
		case "zombie":
		case "z":
			z = new Zombie();
			break;
		case "bucketFace":
		case "w":
			z = new BucketFace();
			break;
		case "sportZomb":
		case "x":
			z = new SportZomb();
			break;
		}
		return z;
	}
	
	//it will use the listCommand command to display the zombies information
	public static String listOfAvilableZombies() {
		String available = "";
		for(int i = 0; i < availableZombies.length; i++) {
			available +="/n" + availableZombies[i].getName() + ": ";
			available += "Price->" + availableZombies[i].getPrice() + "suncoins ";
			available += "Damage->" + availableZombies[i].getDamage();
		}
		return available + "\n\n";
	}
}

