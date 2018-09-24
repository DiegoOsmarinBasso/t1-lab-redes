package connection;

import java.net.*;
import java.io.*;
import tictactoe.Figure;
import tictactoe.TicTacToeMatch;

public class GameServer {

    public GameServer() {
    }

    private void getConnection() {
        // Esperando a conexão dos clientes
        try {
            System.out.println("Waiting for player connections on port 7654.");
            ServerSocket serverSocket = new ServerSocket(7654);
            
            while (true) {
                Socket[] socketList = new Socket[2];
                TicTacToeMatch game = new TicTacToeMatch();
                Figure player = Figure.X;
                for (int i = 0; i < socketList.length; ++i) {
                    Socket connectionSocket = serverSocket.accept();
                    // Adiciona o Socket para a lista
                    socketList[i] = connectionSocket;

                    System.out.println("Player " + Integer.toString(i + 1) + " connected successfully.");

                    GameHandler handler = new GameHandler(connectionSocket, socketList, game, player);
                    Thread thread = new Thread(handler);
                    thread.start();
                    player = Figure.CIRCLE;
                }

                System.out.println("Game running...");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.getConnection();
    }
} // MTServer
