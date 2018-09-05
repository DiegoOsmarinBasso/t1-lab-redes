package tictactoe;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import tictactoe.pieces.O;
import tictactoe.pieces.X;

public class TicTacToeMatch {

	protected static final int BOARD_ROWS = 3;
	protected static final int BOARD_COLUMNS = 3;
	protected static final char COLUMN_INITIAL_LETTER = 'a';

	private Board board;
	private Color currentPlayer;
	private int turn;
	private boolean win;
	private boolean draw;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();

	public TicTacToeMatch() {
		board = new Board(BOARD_ROWS, BOARD_COLUMNS);
		currentPlayer = Color.WHITE;
		turn = 1;
		win = false;
		draw = false;
	}

	public String getTurn() {
		return currentPlayer.getName();
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getWin() {
		return win;
	}

	public boolean getDraw() {
		return draw;
	}

	public TicTacToePiece[][] getPieces() {
		TicTacToePiece[][] mat = new TicTacToePiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (TicTacToePiece) board.piece(i, j);
			}
		}

		return mat;
	}

	private void nextTurn() {
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
		turn++;
	}

	public void placeNewPiece(TicTacToePosition source) {
		Piece piece = (currentPlayer == Color.WHITE) ? new X(board, Color.WHITE) : new O(board, Color.BLACK);
		board.placePiece(piece, new TicTacToePosition(source.getColumn(), source.getRow()).toPosition());
		piecesOnTheBoard.add(piece);

		// Verifica se jogada resultou em vitoria
		if (testWin()) {
			win = true;
			return;
		}

		// Verifica se jogada resultou em empate
		if (turn == BOARD_ROWS * BOARD_COLUMNS) {
			draw = true;
			return;
		}

		// Continua para o proximo turno
		nextTurn();
	}

	private boolean testWin() {
		// verifica diagonais
		int d1 = 0, d2 = 0;
		for (int i = 0; i < BOARD_ROWS; i++) {
			if (board.piece(i, i) != null && ((TicTacToePiece) board.piece(i, i)).getColor() == currentPlayer) {
				d1 += currentPlayer.getValue();
			}
			if (board.piece(i, BOARD_ROWS - i - 1) != null
					&& ((TicTacToePiece) board.piece(i, BOARD_ROWS - i - 1)).getColor() == currentPlayer) {
				d2 += currentPlayer.getValue();
			}
		}
		if (d1 == BOARD_ROWS * currentPlayer.getValue() || d2 == BOARD_ROWS * currentPlayer.getValue()) {
			return true;
		}

		// verifica horizontais
		for (int i = 0; i < BOARD_ROWS; i++) {
			int h = 0;
			for (int j = 0; j < BOARD_COLUMNS; j++) {
				if (board.piece(i, j) != null && ((TicTacToePiece) board.piece(i, j)).getColor() == currentPlayer) {
					h += currentPlayer.getValue();
				}
			}
			if (h == BOARD_ROWS * currentPlayer.getValue()) {
				return true;
			}
		}

		// verifica verticais
		for (int j = 0; j < BOARD_COLUMNS; j++) {
			int v = 0;
			for (int i = 0; i < BOARD_ROWS; i++) {
				if (board.piece(i, j) != null && ((TicTacToePiece) board.piece(i, j)).getColor() == currentPlayer) {
					v += currentPlayer.getValue();
				}
			}
			if (v == BOARD_COLUMNS * currentPlayer.getValue()) {
				return true;
			}
		}

		return false;
	}
}
