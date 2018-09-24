package connection;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class GameClient {

    public static void main(String[] args) {
        try {
            System.out.println("Hello Mate!\nFirst things first, type on the host in which you want to connect:");
            Scanner in = new Scanner(System.in);
            String hostname = in.nextLine();
            int port = 7654;
            System.out.println("Connecting to game server on port " + port);
            Socket connectionSock = new Socket(hostname, port);

            DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

            System.out.println("Connection made.");

            // Comeca uma thread para ouvir e mostrar a informacao vinda do servidor
            GameListener listener = new GameListener(connectionSock);
            Thread theThread = new Thread(listener);
            theThread.start();

            while (serverOutput != null) {
                String data = in.nextLine();
                serverOutput.writeBytes(data + "\n");
            }

            System.out.println("Connection lost.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}