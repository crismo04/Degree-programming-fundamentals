package command.commands;

import command.*;
import controller.Controller;
import logic.Game;
import factory.PlantFactory;
import logic.objets.Plantas;

public class AddCommand extends Command{
	private int x;
	private int y;
	private String plantName;
	
	public AddCommand(){
		super(	"a",
				"[a]dd Planta X Y",
				"Añade una planta en X,Y");	
	}
	
	// usa los metodos del game para ejecutar el comando
	public void execute(Game game, Controller controller) {
		Plantas plant = PlantFactory.getPlant(plantName);
		if(!game.addPlant(plant, x, y)) {
			game.noPasesCiclo();
			controller.noPintesTablero();
		}
	}
	
	// devuelve un objeto de la clase command si coincide con el primer string
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
				System.out.println("El comando add no es correcto");
		}
		return c;
	}
}
