package connection;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GameListener implements Runnable {
    private Socket connectionSock = null;
    private boolean myTurn;

    GameListener(Socket sock) {
        this.connectionSock = sock;
        // this.myTurn = myTurn;
    }

    public void run() {
        // Espera pela informação do servidor, quando/se a receber, a mostra
        try {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
            while (true) {
                if (serverInput == null) {
                    // Connection was lost
                    System.out.println("Closing connection for socket " + connectionSock);
                    connectionSock.close();
                    break;
                }
                // Recebe a informação do servidor
                String serverMessage = serverInput.readLine();

                if (serverMessage.startsWith("+")) {
                    this.myTurn = true;
                } else if (serverMessage.startsWith("-")) {
                    this.myTurn = false;
                } else {
                    System.out.println(serverMessage);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

}
