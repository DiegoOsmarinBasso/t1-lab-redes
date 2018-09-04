package chess;

import boardgame.Position;

public class ChessPosition {

	private char column;
	private int row;

	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8.");
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
		return new Position(ChessMatch.BOARD_ROWS - row, column - ChessMatch.COLUMN_INITIAL_LETTER);
	}

	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) (ChessMatch.COLUMN_INITIAL_LETTER + position.getColumn()),
				ChessMatch.BOARD_ROWS - position.getRow());
	}

	@Override
	public String toString() {
		return "" + column + row;
	}
}
