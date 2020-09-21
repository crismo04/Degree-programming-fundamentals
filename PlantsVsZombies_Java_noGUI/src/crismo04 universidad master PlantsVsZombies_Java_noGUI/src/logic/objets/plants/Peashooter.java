package logic.objets.plants;

import logic.objets.Plants;

public class Peashooter extends Plants {
	
	public Peashooter() {
		super();
		suns = 0;
		health = 3;
		damage = 1;
		price = 50;
		cadence = 1;
		name = "peashooter";
		simb = "p";
	}

	public void update(){
		cycle++;
		if(cycle % cadence == 0)
			game.shoot(damage, x, y);
	}

}
