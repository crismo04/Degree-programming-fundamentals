package command;

import controller.*;
import command.commands.*;

public class CommandParse {
	
	private static Command[] availableCommands = {
			new AddCommand(), 
			new AddZombieCommand(),
			new HelpCommand(),		
			new ResetCommand(),
			new ExitCommand(),
			new ListPlantsCommand(),
			new ListZombiesCommand(),
			new NoneCommand(),
			new PrintModeCommand()
			};
	
	//invokes the parse method of each subclass
	public static Command parseCommand(String[ ] commandWords, Controller controller) {
		int i = 0; 
		boolean find = false;
		Command c = null;
		while(!find && i < availableCommands.length) {
			c = availableCommands[i].parse(commandWords, controller);
			if(c != null)
				find = true;
			else 
				i++;
		}
		return c; // null if command doesn't exist
	}
	
	//invokes the helpText() method of each subclasse
	public static String commandHelp() {
		String list = "";
		int i = 0;
		while(i < availableCommands.length) {
			list += availableCommands[i].helpText();
			i++;
		}
		return list;
	}	
}
