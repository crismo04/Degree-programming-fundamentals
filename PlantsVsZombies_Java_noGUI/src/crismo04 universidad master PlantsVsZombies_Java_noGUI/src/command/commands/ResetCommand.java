package command.commands;

import controller.Controller;
import logic.Game;

public class ResetCommand extends NoParseCommand {

	public ResetCommand(){
		super(	"r",
				"[r]eset",
				"Reset the game from the beginning");
	}
	
	// uses the game's methods to execute the command
	public void execute(Game game, Controller controller) {
		game.resetGame();
	}
}
