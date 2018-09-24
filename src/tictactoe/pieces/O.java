package tictactoe.pieces;

import boardgame.Board;
import tictactoe.Figure;
import tictactoe.TicTacToePiece;

public class O extends TicTacToePiece {

	public O(Board board, Figure figure) {
		super(board, figure);
	}

	@Override
	public String toString() {
		return "O";
	}

}
