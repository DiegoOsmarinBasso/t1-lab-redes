package application;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import tictactoe.Color;
import tictactoe.TicTacToeMatch;
import tictactoe.TicTacToePiece;
import tictactoe.TicTacToePosition;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	// Reset
	public static final String ANSI_RESET = "\u001B[0m";

	// Regular Colors
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	// Bold High Intensity
	public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";
	public static final String RED_BOLD_BRIGHT = "\033[1;91m";
	public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
	public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
	public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";
	public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
	public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";

	// Background
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
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

	private static void printPiece(TicTacToePiece piece, boolean background, boolean selfBackground) {
		if (selfBackground) {
			System.out.print(ANSI_CYAN_BACKGROUND);
		}
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(WHITE_BOLD_BRIGHT + piece + ANSI_RESET);
			} else {
				System.out.print(YELLOW_BOLD_BRIGHT + piece + ANSI_RESET);
			}
		}
	}

	public static void printMatch(TicTacToeMatch ticTacToeMatch, List<TicTacToePiece> captured, String[] args) {

		printTabBoard(ticTacToeMatch.getPieces());

		System.out.println();
		System.out.println("Turn: " + ticTacToeMatch.getTurn());

		if (ticTacToeMatch.getWin()) {
			System.out.println("CHECKMATE!");
			System.out.println("Winner: " + ticTacToeMatch.getCurrentPlayer());
		}
	}

	public static void printTabBoard(TicTacToePiece[][] pieces) {
		int rows = pieces.length;
		int columns = pieces[0].length;

		for (int i = 0; i < rows; i++) {
			System.out.print((rows - i) + "   ");
			for (int j = 0; j < columns; j++) {
				printPiece(pieces[i][j], false, false);
				System.out.print("   ");
			}
			System.out.print("\n\n");
		}
		System.out.println("    a   b   c");
	}

	public static void printTabBoard(TicTacToePiece[][] pieces, boolean[][] possibleMoves, int selfRow,
			int selfColumn) {
		int rows = pieces.length;
		int columns = pieces[0].length;

		for (int i = 0; i < rows; i++) {
			System.out.print((rows - i) + "   ");
			for (int j = 0; j < columns; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j], (selfRow == i && selfColumn == j));
				System.out.print("   ");
			}
			System.out.print("\n\n");
		}
		System.out.println("    a   b   c");
	}

}
