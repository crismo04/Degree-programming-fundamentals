package logic.objets.plants;

import logic.objets.Plants;

public class Sunflower extends Plants{

	public Sunflower() {
		super();
		suns = 10;
		health = 1;
		damage = 0;
		price = 20;
		cadence = 2;	
		name = "sunflower";
		simb = "s";
	}
	
	public void update() {
		cycle++;
		if(cycle > 0 && (cycle * cadence)%2  == 0)
			game.sumSuns(suns);
	}
}
