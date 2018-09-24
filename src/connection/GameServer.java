package connection;

/*
Tic Tac Toe Game Server

created by Shevsi Johnson
Oct 21, 2016

*/

import java.net.*;
import java.io.*;
import java.util.*;

import tictactoe.Figure;
import tictactoe.TicTacToeMatch;

public class GameServer {
    // Maintain list of all client sockets for broadcast

    public GameServer() {
    }

    private void getConnection() {
        // Wait for a connection from the client
        try {
            System.out.println("Waiting for player connections on port 7654.");
            ServerSocket serverSock = new ServerSocket(7654);
            // This is an infinite loop, the user will have to shut it down
            // using control-c

            while (true) {
                Socket[] socketList = new Socket[2];
                TicTacToeMatch game = new TicTacToeMatch();
                Figure player = Figure.X;
                for (int i = 0; i < socketList.length; ++i) {
                    Socket connectionSock = serverSock.accept();
                    // Add this socket to the list
                    socketList[i] = connectionSock;
                    // Send to ClientHandler the socket and arraylist of all sockets

                    System.out.println("Player " + Integer.toString(i + 1) + " connected successfully.");

                    GameHandler handler = new GameHandler(connectionSock, socketList, game, player);
                    Thread theThread = new Thread(handler);
                    theThread.start();
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
