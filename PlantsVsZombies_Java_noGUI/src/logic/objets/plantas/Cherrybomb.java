package logic.objets.plantas;

import logic.objets.*;
public class Cherrybomb extends Plantas{
	
	public Cherrybomb() {
		super();
		soles = 0;
		vida = 2;
		danio = 10;
		coste = 50;
		cadencia = 3;	
		name = "cherrybomb";
		simb = "c";
	}
	
	public void update(){
		ciclos++;
		if(ciclos > 1 && ciclos % cadencia == 0) {
			juego.explota(danio, x, y);
			vida = 0;
		}
	}
}
