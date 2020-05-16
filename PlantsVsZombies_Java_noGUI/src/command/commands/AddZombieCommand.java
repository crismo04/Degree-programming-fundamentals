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
				"Añade un Zombie en X,Y");
	}
	
	// usa los metodos del game para ejecutar el comando
	public void execute(Game game, Controller controller) {
		Zombies z = ZombieFactory.getZombie(zomb);
		if(!game.addZombie(z, x, y)) {
			game.noPasesCiclo();
			controller.noPintesTablero();
		}
	}
	
	// devuelve un objeto de la clase command si coincide con el primer string
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
				System.out.println("El comando add no es correcto");
			}
		}
		return c;
	}
}
