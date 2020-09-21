package command.commands;

import controller.Controller;
import factory.PlantFactory;
import logic.Game;

public class ListPlantsCommand extends NoParseCommand {
	
	public ListPlantsCommand(){
		super(	"lp",
				"[l]ist  of [p]lants",
				"lists the plants available in the game");
	}
	
	//it lists the plants available in the game, whitout draw board or pass cycle
	public void execute(Game game, Controller controller) {
		System.out.println(PlantFactory.listOfAvilablePlants());
		controller.noDrawBoard();
		game.noCyclePass();
	}
}
