package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import tictactoe.TicTacToeMatch;
import tictactoe.TicTacToePiece;
import tictactoe.TicTacToeException;
import tictactoe.TicTacToePosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		TicTacToeMatch ticTacToeMatch = new TicTacToeMatch();
		List<TicTacToePiece> captured = new ArrayList<>();

		while (!ticTacToeMatch.getWin()) {
			try {
				UI.clearScreen();
				UI.printMatch(ticTacToeMatch, captured, args);

				System.out.println();
				System.out.print("Source: ");
				TicTacToePosition source = UI.readTicTacToePosition(sc);

				boolean[][] possibleMoves = ticTacToeMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printTabBoard(ticTacToeMatch.getPieces(), possibleMoves, source.toPosition().getRow(),
						source.toPosition().getColumn());

				System.out.println();
				System.out.print("Target: ");
				TicTacToePosition target = UI.readTicTacToePosition(sc);

				TicTacToePiece capturedPiece = ticTacToeMatch.performChessMove(source, target);

				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}

			} catch (TicTacToeException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(ticTacToeMatch, captured, args);
	}
}
