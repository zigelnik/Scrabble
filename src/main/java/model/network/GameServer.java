package model.network;
import model.GameState;
import model.HostPlayer;
import model.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// Server class
public class GameServer {
    int port;
    private static final int MAX_CLIENTS = 3;
    private static List<GameClientHandler> clients = new ArrayList<>();
    private HostPlayer hostPlayer;
    GameState gameState;
    public GameServer(int port) {
        this.port = port;
        gameState = new GameState();
        hostPlayer = new HostPlayer(gameState);
    }


    public static List<GameClientHandler> getClients() {
        return clients;
    }

    public  void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Server started. Listening on port: "+port);

            Thread hostThread = new Thread(() -> {
                BufferedReader hostReader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        String message = hostReader.readLine();
                        broadcastToClients("Server: " + message);
                        if(message.equals("/start"))
                        {
                            hostPlayer.initGame();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hostThread.start();

            while (true) {
                Socket clientSocket = serverSocket.accept();

                if (clients.size() < MAX_CLIENTS) {
                    Player p = new Player();
                    GameClientHandler gch = new GameClientHandler(clientSocket, p);
                    clients.add(gch);
                    gch.start();
                    gameState.addPlayer(p);

                }
                else {
                    System.out.println("too much clients");
                    clientSocket.close();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastToClients(String message) {
        synchronized (clients) {
            for (GameClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }
    public static void sendStatetoClients(GameState gameState) {
        synchronized (clients) {
            for (GameClientHandler client : clients) {
                client.updateClientsState(gameState);
            }
        }
    }
    public static void removeClient(GameClientHandler gameClientHandler) {
        synchronized (clients) {
            clients.remove(gameClientHandler);
        }
    }
}


