package command;

import logic.Game;
import controller.*;

public abstract class Command {
	private String helpText; 
	private String helpInfo;
	protected final String commandName;
	
	public Command(String commandText, String commandTextMsg, String helpTextMsg){
		commandName = commandText;
		helpText = commandTextMsg;
		helpInfo = helpTextMsg;
	}
	
	// use the game methods to execute the command
	public abstract void execute(Game game, Controller controller);
	
	// returns an object of the command class if it matches the first string
	public abstract Command parse(String[] commandWords, Controller controller);
	
	// returns the help text
	public String helpText(){return " " + helpText + ": " + helpInfo + "\n";}	
}
