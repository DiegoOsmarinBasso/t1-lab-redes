package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import boardgame.BoardException;
import tictactoe.TicTacToeException;
import tictactoe.TicTacToeMatch;
import tictactoe.TicTacToePosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		TicTacToeMatch ticTacToeMatch = new TicTacToeMatch();

		while (!ticTacToeMatch.getWin() && !ticTacToeMatch.getDraw()) {
			try {
				UI.clearScreen();
				UI.printMatch(ticTacToeMatch);

				System.out.println();
				System.out.print(ticTacToeMatch.getTurn() + ", type where you wanto to mark: ");
				TicTacToePosition source = UI.readTicTacToePosition(sc);
				
				ticTacToeMatch.placeNewPiece(source);
				
			} catch (TicTacToeException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (BoardException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(ticTacToeMatch);
	}
}
