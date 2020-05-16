package command.commands;

import controller.Controller;
import logic.Game;

public class ExitCommand extends NoParseCommand {
	
	public ExitCommand(){
		super(	"e",
				"[e]xit",
				"Permite salir del juego");
	}
	
	// usa los metodos del game para ejecutar el comando
	public void execute(Game game, Controller controller) {
		game.jugadorAbandona();
	}
}
