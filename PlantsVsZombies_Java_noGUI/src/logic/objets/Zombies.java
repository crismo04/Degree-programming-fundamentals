package logic.objets;

public abstract class Zombies extends Personaje{
	// cadencia se usa como speed
	public Zombies() {	
		super();
	}
	
	public void update() {
		ciclos++;
		if(juego.vacio(x, y-1)) { // si no hay nada delante
			if(ciclos% cadencia == 0) { // cada speed ciclos, mueve casilla
				y--;
				if(y < 0)
					juego.ZombiesGanan(); // si llegan al final ganan
			}
		}
		else //si hay algo delante ataca (1 vez por turno siempre)
			juego.atacaZ(danio, x, y);
	}
}
