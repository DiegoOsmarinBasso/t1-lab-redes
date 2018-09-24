package connection;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GameListener implements Runnable {
    private Socket connectionSock = null;

    GameListener(Socket sock) {
        this.connectionSock = sock;
    }

    public void run() {
        // Espera pela informação do servidor, quando/se a receber, a mostra.
        try {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
            while (true) {
                if (serverInput == null) {
                    //Conexão foi perdida
                    System.out.println("Closing connection for socket " + connectionSock);
                    connectionSock.close();
                    break;
                }
                // Recebe a informação do servidor
                System.out.println( serverInput.readLine());
               
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

}
