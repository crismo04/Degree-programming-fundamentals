package logic.objets;

public abstract class Zombies extends Character{
	public Zombies() {	
		super();
	}
	
	public void update() {
		cycle++;
		if(game.empty(x, y-1)) { // nothing in front
			if(cycle% cadence == 0) { // each 'speed' cycles
				y--;
				if(y < 0)
					game.ZombieWin(); // if they reach the end they win
			}
		}
		else //if there is something in front of you, attack (always 1 time per shift)
			game.attacksZ(damage, x, y);
	}
}
