/*
 * CRISTIAN MOLINA MUÑOZ
 * 
 * 
 */
package main;

import java.util.Random;
import controller.Controller;
import logic.*;



//Function to read the arguments of the eclipse IDE
public class PlantasVsZombies {

	public static void main(String[] args) {
		Level lvl;
		Random rand;
		long seed;
		Game game;
		Controller cont;
		boolean release = true;
		
		if(args.length < 1 ||args.length > 2) {
			//System.out.println("Arguments error");
			lvl = Level.EASY;
			seed = new Random().nextLong();
			rand = new Random(seed);
			seed = rand.nextLong();
		}
		else {
			if(args[0].equals("EASY"))
				lvl = Level.EASY;
			if(args[0].equals("HARD"))
				lvl = Level.HARD;
			else
				lvl = Level.INSANE;
			
			if(args.length == 2) 
				seed = Long.parseLong(args[1]);
			else {
				seed = new Random().nextLong();
				rand = new Random(seed);
				seed = rand.nextLong();
			}
		}
		game = new Game(lvl,seed);
		cont = new Controller(game, release);
		cont.run();
	}
}
