package print;

import logic.Game;

public class ReleasePrinter extends BoardPrinter {
	private int dimY;
	private String[][] board;
	
	public ReleasePrinter(Game game, int dimX, int y) {
		dim = dimX;
		dimY = y;
		encodeGame(game);
	}
	
	public void encodeGame(Game game) {
		board = new String[dim][dimY];
		for(int i = 0; i < dim; i++) 
			for(int j = 0; j < dimY; j++) 
				board[i][j] = game.toStringRelease(i,j);	
	}
	
	public String PrinterGame() {

		int cellSize = 7;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";
		
		String rowDelimiter = MyStringUtils.repeat(hDelimiter, (dimY * (cellSize + 1)) - 1);
		String margin = MyStringUtils.repeat(space, marginSize);
		String lineDelimiter = String.format("%n%s%s%n", margin + space, rowDelimiter);
		
		StringBuilder str = new StringBuilder();
		
		str.append(lineDelimiter);
		
		for(int i=0; i<dim; i++) {
				str.append(margin).append(vDelimiter);
				for (int j=0; j<dimY; j++) {
					str.append( MyStringUtils.centre(board[i][j], cellSize)).append(vDelimiter);
				}
				str.append(lineDelimiter);
		}
		return str.toString();
	}
}
