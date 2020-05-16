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
	
	// usa los metodos del game para ejecutar el comando
	public abstract void execute(Game game, Controller controller);
	
	// devuelve un objeto de la clase command si coincide con el primer string
	public abstract Command parse(String[] commandWords, Controller controller);
	
	// devuelve el texto de ayuda
	public String helpText(){return " " + helpText + ": " + helpInfo + "\n";}
	
}
