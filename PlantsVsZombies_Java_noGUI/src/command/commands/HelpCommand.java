package command.commands;

import command.*;
import controller.Controller;
import logic.Game;

public class HelpCommand extends NoParseCommand {
	
	public HelpCommand(){
		super(	"h",
				"[h]elp",
				"Proporciona la ayuda sobre los comandos del juego");
	}
	
	// llama al comandParse.helptext para mostrar por pantalla la ayuda
	public void execute(Game game, Controller controller) {
		String help = CommandParse.commandHelp();		
		System.out.print(help);
		controller.noPintesTablero();
		game.noPasesCiclo();
	}
}
