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

	public void noPintesTablero() {
		printB = false;
	}	
	public void SetModo(boolean r) {
		release = r;
	}
	//controla el bucle principal del juego
	 public void run() {
		boolean Salir = false;
		 while(!Salir && game.finJuego() == 0) {
			 if(printB) {
				if(!release) {
					gP = new DebugPrinter(game, game.getDimDebug());
					game.PrintCicloDebug(gP);
				}
				else {
					gP = new ReleasePrinter(game, game.getX(), game.getY());
					game.PrintCicloRelease(gP);
				}
			 }
			 printB = true;
			
			System.out.print("Dame un comando: ");
			String[] s = in.nextLine().toLowerCase().trim().split("\\s+");
			Command command = CommandParse.parseCommand(s, this);				
			if (command != null) 
				command.execute(game, this);
			else {
				System.out.println("No se ha podido ejecutar el comando");
				noPintesTablero();
			}
			game.update();
		 }
		 
		 int fin = game.finJuego();
		 if(fin == 1)
			 System.out.print("LAS PLANTAS GANAN");
		 if(fin == -1)
			 System.out.print("LOS ZOMBIES GANAN");
		 System.out.print("\ngracias por jugar");
	 }	 
}