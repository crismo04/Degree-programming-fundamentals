package command.commands;

import controller.Controller;
import factory.PlantFactory;
import logic.Game;

public class ListPlantsCommand extends NoParseCommand {
	
	public ListPlantsCommand(){
		super(	"lp",
				"[l]ista[p]lantas",
				"lista las plantas disponibles en el juego");
	}
	
	// lista las plantas disponibles en el juego
	public void execute(Game game, Controller controller) {
		System.out.println(PlantFactory.listOfAvilablePlants());
		controller.noPintesTablero();
		game.noPasesCiclo();
	}
}
