package connection;
import java.net.*;
import java.io.*;
import application.UI;
import tictactoe.Figure;
import tictactoe.TicTacToeMatch;
import tictactoe.TicTacToePosition;

public class GameHandler implements Runnable {

    public Socket connectionSock;
    public Socket[] socketList;
    public TicTacToeMatch game;
    public Figure player;

    public GameHandler(Socket socket, Socket[] socketList, TicTacToeMatch game, Figure player) {
        this.connectionSock = socket;
        this.socketList = socketList;
        this.game = game;
        this.player = player;
    }

    public void run() {
        try {
            BufferedReader playerInput = new BufferedReader(
                    new InputStreamReader(this.connectionSock.getInputStream()));

            switch (this.player) {
            case CIRCLE:
                sendMessage("\nYou are player 'CIRCLE', you will go second." + "\r\n");
                sendMessage("-" + "\r\n");
                break;
            case X:
                sendMessage("\nYou are player 'X', you will go first." + "\r\n");
                sendMessage("\nPlease, insert the game's name: " + "\r\n");
               this.game.setMatchName(playerInput.readLine().trim());
                sendMessage("+" + "\r\n");
                break;
            default:
                break;
            }

            while (!this.game.getWin()) {
                sendMessage(UI.printMatch(this.game) + "\r\n");
                if (this.game.getCurrentPlayer() == this.player) {
                    // my turn
                    sendMessage("Player " + player.toString() + " type the collum you want to mark:" + "\r\n");
                    String column = playerInput.readLine().trim();
                    sendMessage("Player " + player.toString() + " type the row you want to mark:" + "\r\n");
                    String row = playerInput.readLine().trim();
                    try {
                        this.game.placeNewPiece(new TicTacToePosition(column.toCharArray()[0], Integer.parseInt(row)));
                        sendMessage("-" + "\r\n");
                    } catch (Exception e) {
                        sendMessage(e.getMessage());
                    }

                } else {
                    //Turno do outro jogador
                    sendMessage("Please wait for opponent's move." + "\r\n");
                    while (this.game.getCurrentPlayer() != this.player) {
                        Thread.sleep(500);
                    }
                    sendMessage("+" + "\r\n");
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException z) {
            System.out.println(z.getMessage());
        }
    }

    private void sendMessage(String message) { // 0 = O, 1 = X, 2 = both
        try {
            DataOutputStream clientOutput = new DataOutputStream(this.connectionSock.getOutputStream());
            clientOutput.writeBytes(message);
            // System.out.println(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
