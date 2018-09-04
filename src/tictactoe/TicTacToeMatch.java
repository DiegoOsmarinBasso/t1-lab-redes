package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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
	private List<Piece> capturedPieces = new ArrayList<>();

	public TicTacToeMatch() {
		board = new Board(BOARD_ROWS, BOARD_COLUMNS);
		turn = 1;
		currentPlayer = Color.WHITE;
		InitialSetup();
	}

	public int getTurn() {
		return turn;
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

	public boolean[][] possibleMoves(TicTacToePosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public TicTacToePiece performChessMove(TicTacToePosition sourcePosition, TicTacToePosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testWin(opponent(currentPlayer))) {
			win = true;
		} else {
			nextTurn();
		}

		return (TicTacToePiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		TicTacToePiece p = (TicTacToePiece) board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		return capturedPiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new TicTacToeException("There is no piece on source position.");
		}
		if (currentPlayer != ((TicTacToePiece) board.piece(position)).getColor()) {
			throw new TicTacToeException("The chosen piece is not yours.");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new TicTacToeException("There is no possible moves for the chosen piece.");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new TicTacToeException("The chosen piece can't move to target position.");
		}
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
			//TODO
		}
		return true;
	}

	private void placeNewPiece(char column, int row, TicTacToePiece piece) {
		board.placePiece(piece, new TicTacToePosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void InitialSetup() {

		placeNewPiece('a', 1, new X(board, Color.WHITE));
		placeNewPiece('b', 1, new X(board, Color.WHITE));
		placeNewPiece('c', 1, new X(board, Color.WHITE));

		placeNewPiece('a', 3, new O(board, Color.BLACK));
		placeNewPiece('b', 2, new O(board, Color.BLACK));
		placeNewPiece('c', 3, new O(board, Color.BLACK));

	}

}
