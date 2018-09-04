package tictactoe.pieces;

import boardgame.Board;
import tictactoe.Color;
import tictactoe.TicTacToePiece;

public class O extends TicTacToePiece {

	public O(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "O";
	}

}
