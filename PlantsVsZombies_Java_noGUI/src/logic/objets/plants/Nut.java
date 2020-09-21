package logic.objets.plants;


import logic.objets.Plants;
public class Nut extends Plants{

	public Nut() {
		super();
		suns = 0;
		health = 10;
		damage = 0;
		price = 50;
		cadence = 1;
		name = "nuez";
		simb = "n";
	}
	
	public void update(){
		cycle++;
	}
	
}
