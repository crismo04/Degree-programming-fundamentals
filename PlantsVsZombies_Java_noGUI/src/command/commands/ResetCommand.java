package command.commands;

import controller.Controller;
import logic.Game;

public class ResetCommand extends NoParseCommand {

	public ResetCommand(){
		super(	"r",
				"[r]eset",
				"Vuenle a iniciar el juego desde el principio");
	}
	
	// usa los metodos del game para ejecutar el comando
	public void execute(Game game, Controller controller) {
		game.resetGame();
	}
}
