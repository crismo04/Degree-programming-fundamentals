package logic.objets.plants;

import logic.objets.*;
public class Cherrybomb extends Plants{
	
	public Cherrybomb() {
		super();
		suns = 0;
		health = 2;
		damage = 10;
		price = 50;
		cadence = 3;	
		name = "cherrybomb";
		simb = "c";
	}
	
	public void update(){
		cycle++;
		if(cycle > 1 && cycle % cadence == 0) {
			game.explota(damage, x, y);
			health = 0;
		}
	}
}
