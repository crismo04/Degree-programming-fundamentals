package command.commands;

import controller.Controller;
import command.Command;
import logic.Game;
import print.*;

public class PrintModeCommand extends Command {
	private boolean modoRelease; //1 = release, 0= debug
	GamePrinter GPrint;
	
	public PrintModeCommand() {
		super(	"p",
				"[P]rint mode",
				"cambia el modo de pintar el tablero");
		modoRelease = true; 								//por defecto modo release
	}
	public void execute(Game game, Controller controller) {
		if(modoRelease)
			GPrint = new DebugPrinter(game, game.getDimDebug());
		else
			GPrint = new ReleasePrinter(game, game.getX(), game.getY());
		game.noPasesCiclo();
		controller.SetModo(modoRelease);
	}
	
	public Command parse(String[] commandWords, Controller controller){
		Command c = null;
		if(commandWords.length == 2) {
			if(commandWords[0].equals(commandName)) { 
				c = this;
				if(commandWords[1].equals("debug"))
					modoRelease = false;
				else if(commandWords[1].equals("release"))
					modoRelease = true;
				else 
					System.out.println("El modo de tablero no es correcto");
				}
			}
		return c;
	}
	
}
