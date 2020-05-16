package command.commands;

import controller.Controller;
import logic.Game;

public class NoneCommand extends NoParseCommand{

	public NoneCommand(){
		super(	"",
				"None",
				"Si no hay comando, salta un ciclo");
	}
	
	// usa los metodos del game para ejecutar el comando
	public void execute(Game game, Controller controller) {
		
	}
}
