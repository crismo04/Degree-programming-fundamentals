package logic.objets.plantas;

import logic.objets.Plantas;

public class Peashooter extends Plantas {
	
	public Peashooter() {
		super();
		soles = 0;
		vida = 3;
		danio = 1;
		coste = 50;
		cadencia = 1;
		name = "peashooter";
		simb = "p";
	}

	public void update(){
		ciclos++;
		if(ciclos % cadencia == 0)
			juego.dispara(danio, x, y);
	}

}
