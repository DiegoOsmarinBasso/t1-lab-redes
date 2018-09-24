package tictactoe.pieces;

import boardgame.Board;
import tictactoe.Figure;
import tictactoe.TicTacToePiece;

public class X extends TicTacToePiece {

	public X(Board board, Figure figure) {
		super(board, figure);
	}

	@Override
	public String toString() {
		return "X";
	}
	
}
