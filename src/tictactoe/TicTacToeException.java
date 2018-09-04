package tictactoe;

import boardgame.BoardException;

public class TicTacToeException extends BoardException {

	private static final long serialVersionUID = 1L;

	public TicTacToeException(String msg) {
		super(msg);
	}
}
