package connection;

import java.net.*;
import java.io.*;
import application.UI;
import tictactoe.Figure;
import tictactoe.TicTacToeMatch;
import tictactoe.TicTacToePosition;

public class GameHandler implements Runnable {

    private Socket connectionSock;
    private Socket[] socketList;
    private TicTacToeMatch game;
    private Figure player;
    private boolean named;

    public GameHandler(Socket socket, Socket[] socketList, TicTacToeMatch game, Figure player) {
        this.connectionSock = socket;
        this.socketList = socketList;
        this.game = game;
        this.player = player;
        this.named = false;
    }

    public void run() {
        try {
            BufferedReader playerInput = new BufferedReader(
                    new InputStreamReader(this.connectionSock.getInputStream()));

            switch (this.player) {
            case CIRCLE:
                sendMessage("\nOk mate, you are player 'CIRCLE', and you will go second." + "\r\n");
                sendMessage("-" + "\r\n");
                break;
            case X:
                sendMessage("\nOkay, now, could you please, insert the game's name: " + "\r\n");
                this.game.setMatchName(playerInput.readLine().trim());
                sendMessage(
                        "\nMarvelous, you are player 'X', that means you will go first in the game as soon as the other player connect."
                                + "\r\n\n");
                sendMessage("+" + "\r\n");
                break;
            default:
                break;
            }

            while (!this.game.getWin() && !this.game.getDraw()) {
                while (socketList[1] == null) {
                    Thread.sleep(250);
                }
                sendMessage("Alright there fella, player 2 connected!\n");
                sendMessage(UI.printMatch(this.game) + "\r\n");
                if (this.game.getCurrentPlayer() == this.player) {
                    // Turno do jogador atual
                    sendMessage("Player " + player.toString() + " type the collum you want to mark:" + "\r\n");
                    String column = playerInput.readLine().trim();
                    sendMessage("Player " + player.toString() + " type the row you want to mark:" + "\r\n");
                    String row = playerInput.readLine().trim();
                    try {
                        this.game.placeNewPiece(new TicTacToePosition(column.toCharArray()[0], Integer.parseInt(row)));
                        sendMessage("-" + "\r\n");
                    } catch (Exception e) {
                        sendMessage(e.getMessage() + "\n");
                    }

                } else {
                    // Turno do outro jogador
                    sendMessage("Please wait for opponent's move." + "\r\n");
                    while (this.game.getCurrentPlayer() != this.player) {
                        Thread.sleep(500);
                    }
                    sendMessage("+" + "\r\n");
                }
            }
            
            sendMessage(UI.printMatch(this.game));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException z) {
            System.out.println(z.getMessage());
        }
    }

    private void sendMessage(String message) {
        try {
            DataOutputStream clientOutput = new DataOutputStream(this.connectionSock.getOutputStream());
            clientOutput.writeBytes(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
