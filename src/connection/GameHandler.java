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

            // Determina inicialmente quem são os jogadores desta partida
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
            while (true) {
                while (socketList[1] == null) {
                    Thread.sleep(250);
                }
                sendMessage(UI.printMatch(this.game) + "\r\n");
                // Ultimas alterções de turno
                if (game.getDraw() || game.getWin()) {
                    if (this.game.getCurrentPlayer().equals(Figure.X)) {
                        this.game.setCurrentPlayer(Figure.CIRCLE);
                    } else {
                        this.game.setWinner(Figure.CIRCLE);
                        this.game.setCurrentPlayer(Figure.X);
                    }
                    break;
                }
                if (this.game.getCurrentPlayer() == this.player && !game.getDraw() && !game.getWin()) {
                    // Turno do jogador atual
                    sendMessage("Player " + player.toString() + " type the collum you want to mark:" + "\r\n");
                    String column = playerInput.readLine().trim();
                    sendMessage("Player " + player.toString() + " type the row you want to mark:" + "\r\n");
                    String row = playerInput.readLine().trim();
                    try {
                        this.game.placeNewPiece(new TicTacToePosition(column.toCharArray()[0], Integer.parseInt(row)));
                    } catch (Exception e) {
                        sendMessage(e.getMessage() + "\n");
                    }

                } else {
                    // Turno do outro jogador
                    if (!game.getDraw() && !game.getWin())
                        sendMessage("Please wait for opponent's move." + "\r\n");
                    while (this.game.getCurrentPlayer() != this.player) {
                        Thread.sleep(500);
                    }
                }
            }
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException z) {
            System.out.println(z.getMessage());
        }
    }

    // Método que formata e envia a mensagem para o cliente
    private void sendMessage(String message) {
        try {
            DataOutputStream clientOutput = new DataOutputStream(this.connectionSocket.getOutputStream());
            clientOutput.writeBytes(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    //Verifica se existe espaço na sala para um jogador entrar
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
