package command.commands;

import command.*;
import controller.Controller;
import logic.Game;

public class HelpCommand extends NoParseCommand {
	
	public HelpCommand(){
		super(	"h",
				"[h]elp",
				"Provides help on game commands");
	}
	
	//This method call the commandParse.helptext to display the help, whitout pass cycle or draw board
	public void execute(Game game, Controller controller) {
		String help = CommandParse.commandHelp();		
		System.out.print(help);
		controller.noDrawBoard();
		game.noCyclePass();
	}
}
