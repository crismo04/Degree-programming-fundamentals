package command.commands;

import controller.Controller;
import factory.ZombieFactory;
import logic.Game;

public class ListZombiesCommand extends NoParseCommand{

	public ListZombiesCommand(){
		super(	"lz",
				"[l]ist of [z]ombies",
				"Lists the zombies available in the game");
	}
	
	//it uses the game's methods to execute the command
	public void execute(Game game, Controller controller) {
		System.out.println(ZombieFactory.listOfAvilableZombies());
		controller.noDrawBoard();
		game.noCyclePass();
	}
}
