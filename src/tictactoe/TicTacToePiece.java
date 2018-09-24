package tictactoe;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public class TicTacToePiece extends Piece {

    private Figure figure;

    public TicTacToePiece(Board board, Figure figure) {
        super(board);
        this.figure = figure;
    }

    public Figure getFigure() {
        return figure;
    }

    public TicTacToePosition getTicTacToePosition() {
        return TicTacToePosition.fromPosition(position);
    }

    protected boolean isThereAnyPiece(Position position) {
        TicTacToePiece p = (TicTacToePiece) getBoard().piece(position);
        return p != null;
    }

}
