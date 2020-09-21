package logic.objets;

import logic.Game;

public abstract class Character {
	protected int x;
	protected int y;
	protected Game game;
	protected int cycle;
	protected int health;
	protected int damage;
	protected int price;
	protected int cadence;
	protected String name;
	protected String simb;
	
	public Character() {
		cycle =0;
	}
	
	// adds to a character the position and the game
	public void positioning(int row, int colum, Game g) {
		x = row;
		y = colum;
		game = g;
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
	public int getHealth() {
		return health;
	}
	public int getPrice() {
		return price;
	}
	public int getDamage() {
		return damage;
	}
	public int GetCurrentTime() {
		return (cycle%cadence);
	}
	public void subHealth(int damage) {
		if(health - damage < 0)
			health = 0;
		else
			health -= damage;
	}
	public abstract void update();
	
}
