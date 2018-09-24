package connection;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.*;
import tictactoe.Figure;
import tictactoe.TicTacToeMatch;

public class GameServer {
    private List<GameHandler> handlers;

    public GameServer() {
        handlers = new ArrayList<>();
    }

    private void getConnection() throws InterruptedException {
        // Esperando a conexão dos clientes
        try {
            System.out.println("Waiting for player connections on port 7654.");
            ServerSocket serverSocket = new ServerSocket(7654);
            while (true) {
                // Recebe a conexão do cliente
                Socket connectionSocket = serverSocket.accept();
                TicTacToeMatch game = new TicTacToeMatch();
                DataOutputStream clientOutput = new DataOutputStream(connectionSocket.getOutputStream());
                clientOutput.writeBytes("Alright then, following there is a list of avaiable games, would you like "
                        + "to enter one, or to create a new one? (enter 1 for creating a new game, "
                        + "or type in the name of game you want to enter: \n");
                clientOutput.writeBytes(listAvailableGames());
                BufferedReader playerInput = new BufferedReader(
                        new InputStreamReader(connectionSocket.getInputStream()));
                String response = playerInput.readLine().trim();

                // Cria um novo jogo e um novo handler para o mesmo.
                if (response.equalsIgnoreCase("1")) {
                    clientOutput.writeBytes("\nOkay then, could you please, insert the new game's name: " + "\r\n");
                    String name = playerInput.readLine().trim();
                    while (!isNameAvailable(name)) {
                        clientOutput.writeBytes("\nOps, invalid name, please, choose another one: " + "\r\n");
                        name = playerInput.readLine().trim();
                    }

                    Socket[] socketList = new Socket[2];
                    Figure player = Figure.X;
                    socketList[0] = connectionSocket;
                    game.setMatchName(name);
                    GameHandler gameHandler = new GameHandler(connectionSocket, socketList, game, player);
                    handlers.add(gameHandler);
                    Thread thread = new Thread(gameHandler);
                    thread.start();
                    // Jogador entra em um jogo
                } else {
                    Optional<GameHandler> handler = findHandler(response);
                    while (!handler.isPresent()) {
                        clientOutput.writeBytes("Invalid game name, please type an correct one/n");
                        response = playerInput.readLine().trim();
                        handler = findHandler(response);
                    }
                    //Configurções do jogo são criadas
                    Socket[] socketList = handler.get().getSocketList();
                    socketList[1] = connectionSocket;
                    Figure player = Figure.CIRCLE;
                    GameHandler gameHandler = new GameHandler(connectionSocket, socketList, handler.get().getGame(),
                            player);
                    handlers.add(gameHandler);
                    Thread thread = new Thread(gameHandler);
                    thread.start();
                }

                System.out.println("A player connected successfully.");

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GameServer server = new GameServer();
        server.getConnection();
    }
    
    //Método que lista os jogos disponíveis
    private String listAvailableGames() {
        StringBuilder str = new StringBuilder();
        for (GameHandler handler : handlers) {
            if (handler.isGameAvailable())
                str.append(handler.getGameInfo() + "\n");
        }

        if (str.toString().isEmpty())
            return "No available games. Sorry mate :(\n";
        return str.toString();
    }
    //Método que verifica se nome do jogo está disponível
    private boolean isNameAvailable(String name) {
        if (name.equals("1") || name.length() < 1)
            return false;
        for (GameHandler handler : handlers) {
            if (handler.getGameInfo().equals(name))
                return false;
        }
        return true;
    }
    //Classe auxiliar que acha o handler desejado
    private Optional<GameHandler> findHandler(String name) {
        return handlers.stream().filter(g -> g.getGameInfo().equals(name)).findFirst();
    }
}
