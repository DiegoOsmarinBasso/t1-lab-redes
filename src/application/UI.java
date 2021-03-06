package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import tictactoe.Figure;
import tictactoe.TicTacToeMatch;
import tictactoe.TicTacToePiece;
import tictactoe.TicTacToePosition;

public class UI {

    public static void clearScreen() {
        System.out.flush();
    }

    public static TicTacToePosition readTicTacToePosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));

            return new TicTacToePosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error reading TicTacToePosition. Valid values are from a1 to c3.");
        }
    }

    private static String printPiece(TicTacToePiece piece) {
        StringBuilder str = new StringBuilder();
        if (piece == null) {
            str.append("-");
        } else {
            if (piece.getFigure() == Figure.X) {
                str.append("X");
            } else {
                str.append("O");
            }
        }
        return str.toString();
    }

    public static String printMatch(TicTacToeMatch ticTacToeMatch) {
        StringBuilder str = new StringBuilder();
        str.append("Game: " + ticTacToeMatch.getMatchName() + "\n");
        str.append(printTabBoard(ticTacToeMatch.getPieces()));

        if (ticTacToeMatch.getWin()) {
            str.append("\nTIC TAC MATE!\n")
               .append("Winner: " + ticTacToeMatch.getWinner() + "!\n");
        }

        if (ticTacToeMatch.getDraw()) {
            str.append("\nTIC TAC DRAW!\n")
               .append("The game has came to a tie\n");
        }
        return str.toString();
    }

    public static String printTabBoard(TicTacToePiece[][] pieces) {
        StringBuilder str = new StringBuilder();
        int rows = pieces.length;
        int columns = pieces[0].length;

        for (int i = 0; i < rows; i++) {
            str.append((rows - i) + "   ");
            for (int j = 0; j < columns; j++) {
               str.append(printPiece(pieces[i][j]));
                str.append(("   "));
            }
            str.append(("\n\n"));
        }
        str.append(("    a   b   c"));
        return str.toString();
    }

}
