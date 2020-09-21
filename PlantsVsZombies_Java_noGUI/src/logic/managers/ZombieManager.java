package logic.managers;

import logic.Level;
import java.util.Random;

// keeps track of the zombies that are left
public class ZombieManager {
	private int howMany;
	private int howManyLive;
	private Level lvl;
	
	public ZombieManager(Level l){
		lvl = l;
		howMany = l.getNumZombies();
		howManyLive = 0;
	}
	
	public int HowMany() {
		return howMany;
	}
	public int HowManyLive() {
		return howManyLive;
	}
	
	// To know if it is necessary to add a zombie or not 
	public boolean isZombieAdded(Random ran) {
		boolean b = false;
		double prob = ran.nextDouble();
		if((prob%1 < lvl.getFrecuency()) && (howMany > 0)) {
			b = true;
			howManyLive++;
			howMany--;
		}
		return b;
	}
	//return true if there are no zombies left
	public boolean AllDead() {
		boolean b = false;
		if(howManyLive == 0 && howMany == 0)
			b = true;
		return b;
	}
	public void killZombie() {
			howManyLive--;
	}

}
