package print;

import logic.Game;

public abstract class BoardPrinter implements GamePrinter {
	int dim; 
	final String space = " ";
	
	public BoardPrinter(){}
	
	protected abstract void encodeGame(Game game);
}
