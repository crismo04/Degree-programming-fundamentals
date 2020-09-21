package command.commands;

import controller.Controller;
import logic.Game;

public class NoneCommand extends NoParseCommand{

	public NoneCommand(){
		super(	"",
				"None",
				"If there is no command, it skips a cycle");
	}
	
	//it don't do nothig
	public void execute(Game game, Controller controller) {	}
}
