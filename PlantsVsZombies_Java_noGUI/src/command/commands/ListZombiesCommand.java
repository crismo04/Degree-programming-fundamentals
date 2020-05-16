package command.commands;

import controller.Controller;
import factory.ZombieFactory;
import logic.Game;

public class ListZombiesCommand extends NoParseCommand{

	public ListZombiesCommand(){
		super(	"lz",
				"[l]ista[z]ombies",
				"Lista los zombies disponibles en el juego");
	}
	
	// usa los metodos del game para ejecutar el comando
	public void execute(Game game, Controller controller) {
		System.out.println(ZombieFactory.listOfAvilableZombies());
		controller.noPintesTablero();
		game.noPasesCiclo();
	}
}
