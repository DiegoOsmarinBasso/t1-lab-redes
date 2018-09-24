package connection;

import java.net.*;
import java.io.*;
import application.UI;
import tictactoe.Figure;
import tictactoe.TicTacToeMatch;
import tictactoe.TicTacToePosition;

public class GameHandler implements Runnable {

    private Socket connectionSocket;
    private Socket[] socketList;
    private TicTacToeMatch game;
    private Figure player;
    private boolean named;

    public GameHandler(Socket socket, Socket[] socketList, TicTacToeMatch game, Figure player) {
        this.connectionSocket = socket;
        this.socketList = socketList;
        this.game = game;
        this.player = player;
    }

    
    public void run() {
        try {
            BufferedReader playerInput = new BufferedReader(
                    new InputStreamReader(this.connectionSocket.getInputStream()));

            switch (this.player) {
            case CIRCLE:
                sendMessage("\nOk mate, you are player 'CIRCLE', and you will go second." + "\r\n");
                sendMessage("-" + "\r\n");
                break;
            case X:
                this.named = true;
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
         // TODO recomecar partida depois que acabou
            // TODO descobrir como printar a mensagem de fim de jogo para ambos, por
            // enquanto so printa para o vencedor
            sendMessage(UI.printMatch(this.game));
            sendMessage("-" + "\r\n");
            sendMessage(UI.printMatch(this.game));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException z) {
            System.out.println(z.getMessage());
        }
    }

    private void sendMessage(String message) {
        try {
            DataOutputStream clientOutput = new DataOutputStream(this.connectionSocket.getOutputStream());
            clientOutput.writeBytes(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isGameAvailable() {
        if (socketList[1] != null)
            return false;
        return true;
    }

    public String getGameInfo() {
        return this.game.getMatchName();
    }

    public boolean isNamed() {
        return named;
    }

    public TicTacToeMatch getGame() {
        return game;
    }

    public Socket[] getSocketList() {
        return socketList;
    }

}
