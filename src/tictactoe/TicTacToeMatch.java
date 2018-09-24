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
    private Figure currentPlayer;
    private int turn;
    private boolean win;
    private boolean draw;
    private String matchName;
    private Figure winner;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();

    public Figure getWinner() {
        return winner;
    }

    public void setWinner(Figure winner) {
        this.winner = winner;
    }

    public TicTacToeMatch() {
        board = new Board(BOARD_ROWS, BOARD_COLUMNS);
        currentPlayer = Figure.X;
        turn = 1;
        win = false;
        draw = false;
    }

    public String getTurn() {
        return currentPlayer.getName();
    }

    public Figure getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getWin() {
        return win;
    }

    public boolean getDraw() {
        return draw;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public void setCurrentPlayer(Figure player) {
        this.currentPlayer = player;
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
        currentPlayer = (currentPlayer == Figure.X) ? Figure.CIRCLE : Figure.X;
        turn++;
    }

    public void placeNewPiece(TicTacToePosition source) {
        Piece piece = (currentPlayer == Figure.X) ? new X(board, Figure.X) : new O(board, Figure.CIRCLE);
        board.placePiece(piece, new TicTacToePosition(source.getColumn(), source.getRow()).toPosition());
        piecesOnTheBoard.add(piece);

        // Verifica se jogada resultou em vitoria
        if (testWin()) {
            win = true;
            winner = currentPlayer;
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
            if (board.piece(i, i) != null && ((TicTacToePiece) board.piece(i, i)).getFigure() == currentPlayer) {
                d1 += currentPlayer.getValue();
            }
            if (board.piece(i, BOARD_ROWS - i - 1) != null
                    && ((TicTacToePiece) board.piece(i, BOARD_ROWS - i - 1)).getFigure() == currentPlayer) {
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
                if (board.piece(i, j) != null && ((TicTacToePiece) board.piece(i, j)).getFigure() == currentPlayer) {
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
                if (board.piece(i, j) != null && ((TicTacToePiece) board.piece(i, j)).getFigure() == currentPlayer) {
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
