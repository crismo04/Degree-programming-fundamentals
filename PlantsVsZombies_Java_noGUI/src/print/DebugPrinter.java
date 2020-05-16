package print;

import logic.Game;

public class DebugPrinter extends BoardPrinter {
	private String[] board;
	
	public DebugPrinter(Game game, int dimDebug) {
		dim = dimDebug;
		encodeGame(game);
	}
	
	public void encodeGame(Game game) {
		board = new String[dim];
		for(int i = 0; i < dim; i++) 
			board[i] = game.toStringDebug(i);
	}
	
	public String PrinterGame() {
		int cellSize = 20;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";
		
		String rowDelimiter = MyStringUtils.repeat(hDelimiter, ((cellSize + 1)) - 1);
		String margin = MyStringUtils.repeat(space, marginSize);
		String lineDelimiter = String.format("%n%s%s%n", margin + space, rowDelimiter);
		
		StringBuilder str = new StringBuilder();
		
		str.append(lineDelimiter);
		
		for(int i=0; i<dim; i++) {
				str.append(margin).append(vDelimiter);
				str.append( MyStringUtils.centre(board[i], cellSize)).append(vDelimiter);
				str.append(lineDelimiter);
		}
		return str.toString();
	}
}
