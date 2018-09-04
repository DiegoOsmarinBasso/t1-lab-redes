package tictactoe.pieces;

import boardgame.Board;
import tictactoe.Color;
import tictactoe.TicTacToePiece;

public class X extends TicTacToePiece {

	public X(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "X";
	}
	
}
