package tictactoe;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public class TicTacToePiece extends Piece {

	private Color color;

	public TicTacToePiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public TicTacToePosition getTicTacToePosition() {
		return TicTacToePosition.fromPosition(position);
	}

	protected boolean isThereAnyPiece(Position position) {
		TicTacToePiece p = (TicTacToePiece) getBoard().piece(position);
		return p != null;
	}

}
