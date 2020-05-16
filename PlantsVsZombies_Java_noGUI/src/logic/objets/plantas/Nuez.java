package logic.objets.plantas;


import logic.objets.Plantas;
public class Nuez extends Plantas{

	public Nuez() {
		super();
		soles = 0;
		vida = 10;
		danio = 0;
		coste = 50;
		cadencia = 1;
		name = "nuez";
		simb = "n";
	}
	
	public void update(){
		ciclos++;
	}
	
}
