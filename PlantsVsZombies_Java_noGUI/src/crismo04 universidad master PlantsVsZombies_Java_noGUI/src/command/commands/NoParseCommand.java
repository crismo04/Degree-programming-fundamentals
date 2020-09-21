package command.commands;

import command.*;
import controller.Controller;
import logic.Game;

//class from which classes without parameters are inherited
public abstract class NoParseCommand extends Command{
	
	public NoParseCommand(String commandText, String commandTextMsg, String helpTextMsg) {
		super(commandText, commandTextMsg, helpTextMsg);
	}
	
	//it uses the game's methods to execute the command
	public abstract void execute(Game game, Controller controller);
	
	//it returns an object of the command class if it matches the first string
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		if(commandWords[0].equals(commandName))
			c = this;
		return c;
	}
}
