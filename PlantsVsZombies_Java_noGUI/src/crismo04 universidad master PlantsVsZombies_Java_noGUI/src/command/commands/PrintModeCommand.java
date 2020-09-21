package command.commands;

import controller.Controller;
import command.Command;
import logic.Game;
import print.*;

public class PrintModeCommand extends Command {
	private boolean Release; //1 = release, 0= debug
	GamePrinter GPrint;
	
	public PrintModeCommand() {
		super(	"p",
				"[P]rint mode",
				"change the mode the board is painting");
		Release = true; 								//Release mode default
	}
	public void execute(Game game, Controller controller) {
		if(Release)
			GPrint = new DebugPrinter(game, game.getDimDebug());
		else
			GPrint = new ReleasePrinter(game, game.getX(), game.getY());
		game.noCyclePass();
		controller.SetMode(Release);
	}
	
	public Command parse(String[] commandWords, Controller controller){
		Command c = null;
		if(commandWords.length == 2) {
			if(commandWords[0].equals(commandName)) { 
				c = this;
				if(commandWords[1].equals("debug"))
					Release = false;
				else if(commandWords[1].equals("release"))
					Release = true;
				else 
					System.out.println("The board mode is incorrect");
				}
			}
		return c;
	}
	
}
