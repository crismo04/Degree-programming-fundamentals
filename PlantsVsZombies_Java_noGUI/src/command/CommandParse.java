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
	
	//invoca el método parse de cada subclase
	public static Command parseCommand(String[ ] commandWords, Controller controller) {
		int i = 0; 
		boolean encontrado = false;
		Command c = null;
		while(!encontrado && i < availableCommands.length) {
			c = availableCommands[i].parse(commandWords, controller);
			if(c != null)
				encontrado = true;
			else 
				i++;
		}
		return c; // null si no hay comando
	}
	
	// invocando el método helpText() de cada subclase
	public static String commandHelp() {
		String lista = "";
		int i = 0;
		while(i < availableCommands.length) {
			lista += availableCommands[i].helpText();
			i++;
		}
		return lista;
	}	
}
