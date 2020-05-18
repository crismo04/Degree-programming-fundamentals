package controller;

import java.util.Scanner;
import logic.Game;
import command.*;
import print.*;
public class Controller {
	private Game game;
	private Scanner in;
	private boolean printB;
	private GamePrinter gP;
	private boolean release;
	
	public Controller(Game g, boolean r){
		game = g;
		in = new Scanner(System.in);
		printB = true;
		release = r;
	}

	public void noDrawBoard() {
		printB = false;
	}	
	public void SetMode(boolean r) {
		release = r;
	}
	//run the main loop of the game
	 public void run() {
		boolean exit = false;
		 while(!exit && game.gameOver() == 0) {
			 if(printB) {
				if(!release) {
					gP = new DebugPrinter(game, game.getDimDebug());
					game.PrintCycleDebug(gP);
				}
				else {
					gP = new ReleasePrinter(game, game.getX(), game.getY());
					game.PrintCycleRelease(gP);
				}
			 }
			 printB = true;
			
			System.out.print("Enter a command: ");
			String[] s = in.nextLine().toLowerCase().trim().split("\\s+");
			Command command = CommandParse.parseCommand(s, this);				
			if (command != null) 
				command.execute(game, this);
			else {
				System.out.println("The command could not be executed");
				noDrawBoard();
			}
			game.update();
		 }
		 
		 int fin = game.finJuego();
		 if(fin == 1)
			 System.out.print("PLANTS WIN");
		 if(fin == -1)
			 System.out.print("ZOMBIES WIN");
		 System.out.print("\nThanks for playing PvZ Eclipse Console Version.");
	 }	 
}
