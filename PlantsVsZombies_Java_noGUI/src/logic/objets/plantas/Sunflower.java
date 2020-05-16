package logic.objets.plantas;

import logic.objets.Plantas;

public class Sunflower extends Plantas{

	public Sunflower() {
		super();
		soles = 10;
		vida = 1;
		danio = 0;
		coste = 20;
		cadencia = 2;	
		name = "sunflower";
		simb = "s";
	}
	
	public void update() {
		ciclos++;
		if(ciclos > 0 && (ciclos * cadencia)%2  == 0)
			juego.sumaSuns(soles);
	}
}
