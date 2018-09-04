package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import tictactoe.pieces.O;
import tictactoe.pieces.X;

public class TicTacToeMatch {

	protected static final int BOARD_ROWS = 3;
	protected static final int BOARD_COLUMNS = 3;
	protected static final char COLUMN_INITIAL_LETTER = 'a';

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean win;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();

	public TicTacToeMatch() {
		board = new Board(BOARD_ROWS, BOARD_COLUMNS);
		turn = 1;
		currentPlayer = Color.WHITE;
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
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private boolean testWin(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((TicTacToePiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			// TODO
		}
		return true;
	}

	public boolean[][] possibleMoves(TicTacToePosition source) {
		// TODO Auto-generated method stub
		return new boolean[][] {{true, true, true},{true, true, true},{true, true, true}};
	}

	public void placeNewPiece(TicTacToePosition source) {
		Piece piece = (currentPlayer == Color.WHITE) ? new X(board, Color.WHITE) : new O(board, Color.BLACK);
		board.placePiece(piece, new TicTacToePosition(source.getColumn(), source.getRow()).toPosition());
		piecesOnTheBoard.add(piece);

		testWin(currentPlayer);
		nextTurn();
	}
}
