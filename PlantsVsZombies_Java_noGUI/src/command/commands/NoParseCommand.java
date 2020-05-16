package command.commands;

import command.*;
import controller.Controller;
import logic.Game;

//clase de la que heredan las clases que no contienen parametros
public abstract class NoParseCommand extends Command{
	
	public NoParseCommand(String commandText, String commandTextMsg, String helpTextMsg) {
		super(commandText, commandTextMsg, helpTextMsg);
	}
	
	// usa los metodos del game para ejecutar el comando
	public abstract void execute(Game game, Controller controller);
	
	// devuelve un objeto de la clase command si coincide con el primer string
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		if(commandWords[0].equals(commandName))
			c = this;
		return c;
	}
}