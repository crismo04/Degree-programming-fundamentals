package command.commands;

import controller.Controller;
import logic.Game;

public class ExitCommand extends NoParseCommand {
	
	public ExitCommand(){
		super(	"e",
				"[e]xit",
				"Allows you to get out of the game");
	}
	
	//It uses the game's methods to execute the command
	public void execute(Game game, Controller controller) {
		game.playerLeaves();
	}
}
