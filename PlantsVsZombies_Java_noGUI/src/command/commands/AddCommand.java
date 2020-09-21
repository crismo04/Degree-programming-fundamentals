package command.commands;

import command.*;
import controller.Controller;
import logic.Game;
import factory.PlantFactory;
import logic.objets.Plants;

public class AddCommand extends Command{
	private int x;
	private int y;
	private String plantName;
	
	public AddCommand(){
		super(	"a",
				"[a]dd Plant X Y",
				"Add the specified plant in X,Y");	
	}
	
	// uses the game's methods to execute the command
	public void execute(Game game, Controller controller) {
		Plants plant = PlantFactory.getPlant(plantName);
		if(!game.addPlant(plant, x, y)) {
			game.noCyclePass();
			controller.noDrawBoard();
		}
	}
	
	// returns an object of the command class if it matches the first string
	public Command parse(String[] commandWords, Controller controller) {
		Command c = null;
		if(commandWords[0].equals(commandName)) { 
			if(commandWords.length == 4) {
				c = this;
				plantName = commandWords[1];
				x = Integer.parseInt(commandWords[2]);
			y = Integer.parseInt(commandWords[3]);
			}
			else
				System.out.println("The add command is not correct");
		}
		return c;
	}
}
