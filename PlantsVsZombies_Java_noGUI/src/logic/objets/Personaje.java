package logic.objets;

import logic.Game;

public abstract class Personaje {
	protected int x;
	protected int y;
	protected Game juego;
	protected int ciclos;
	protected int vida;
	protected int danio;
	protected int coste;
	protected int cadencia;
	protected String name;
	protected String simb;
	
	public Personaje() {
		ciclos =0;
	}
	
	// añade a un personaje la posicion y el game
	public void posicionar(int fila, int colum, Game game) {
		x = fila;
		y = colum;
		juego = game;
	}

	public String getName() {
		return name;
	}
	public String getSim() {
		return simb;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getVida() {
		return vida;
	}
	public int getCoste() {
		return coste;
	}
	public int getDanio() {
		return danio;
	}
	public int GetTiempoAct() {
		return (ciclos%cadencia);
	}
	public void quitarVida(int damage) {
		if(vida - damage < 0)
			vida = 0;
		else
			vida -= damage;
	}
	public abstract void update();
	
}
