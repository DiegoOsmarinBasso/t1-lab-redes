package tictactoe;

import boardgame.Position;

public class TicTacToePosition {

	private char column;
	private int row;

	public TicTacToePosition(char column, int row) {
		if (column < 'a' || column > 'c' || row < 1 || row > 3) {
			throw new TicTacToeException("Error instantiating TicTacToePosition. Valid values are from a1 to c3.");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public Position toPosition() {
		return new Position(TicTacToeMatch.BOARD_ROWS - row, column - TicTacToeMatch.COLUMN_INITIAL_LETTER);
	}

	public static TicTacToePosition fromPosition(Position position) {
		return new TicTacToePosition((char) (TicTacToeMatch.COLUMN_INITIAL_LETTER + position.getColumn()),
				TicTacToeMatch.BOARD_ROWS - position.getRow());
	}

	@Override
	public String toString() {
		return "" + column + row;
	}
}
