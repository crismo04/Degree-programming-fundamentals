package command.commands;

import command.Command;
import controller.Controller;
import factory.ZombieFactory;
import logic.Game;
import logic.objets.Zombies;

public class AddZombieCommand extends Command{
	private int x;
	private int y;
	private String zomb;
	
	public AddZombieCommand(){
		super(	"az",
				"[a]dd[z]ombie Zombie X Y",
				"Add the specified Zombie in X,Y");
	}
	
	// uses the game's methods to execute the command
	public void execute(Game game, Controller controller) {
		Zombies z = ZombieFactory.getZombie(zomb);
		if(!game.addZombie(z, x, y)) {
			game.noCyclePass();
			controller.noDrawBoard();
		}
	}
	
	// returns an object of the command class if it matches the first string
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		if(commandWords[0].equals(commandName)) { 
			if(commandWords.length == 4) {
				c = this;
				zomb = commandWords[1];
				x = Integer.parseInt(commandWords[2]);
				y = Integer.parseInt(commandWords[3]);		
			}
			else {
				System.out.println("The add command is not correct");
			}
		}
		return c;
	}
}
