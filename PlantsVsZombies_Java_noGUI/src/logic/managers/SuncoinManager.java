package logic.managers;

public class SuncoinManager {
	private int numSoles;
	
	public SuncoinManager() {
		numSoles = 50;
	}
	
	//suma los SunCoins depediendo del numero de girasoles
	public void sumaSC(int soles) {
		numSoles += soles;
	}
	
	//resta el precio a la cantidad de monedas
	public void RestaSC(int Precio) {
		numSoles -= Precio;
	}
	
	//devuelve si hay suficientes monedas
	public boolean SuficientesSC(int precio) {
		boolean b;
		if(precio > numSoles) b = false;
		else b = true;
		return b;
	}
	
	public int getSunCoins() {
		return numSoles;
	}
}
