package model.network;

import model.concrete.GameState;
import model.concrete.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**

 The GameServer class represents a server that manages the game and handles communication with clients.

 It listens for incoming client connections and assigns each client to a separate thread for processing.
 */

// Server class
public class GameServer {
    int port;
    public volatile boolean stop = false;
    private static final int MAX_CLIENTS = 3;
    public static List<GameClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);

    /**

     Constructs a GameServer object with the specified port.
     @param port The port number on which the server listens for incoming connections.
     */
    public GameServer(int port) {
        this.port = port;

    }
    /**

     Returns the list of currently connected clients.
     @return The list of GameClientHandler objects representing the connected clients.
     */
    public static List<GameClientHandler> getClients() {
        return clients;
    }

    /**

     Starts the game server and begins accepting incoming connections from clients.
     */
    public  void start() {
        try {

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port: "+port);

            while (!stop) {
                Socket clientSocket = serverSocket.accept();

                if (clients.size() == MAX_CLIENTS) {
                    System.out.println("too much clients");
                    clientSocket.close();
                    continue;
                }
                // run each client in a different Thread
                executorService.execute(()->{
                    Player p = new Player();
                    GameClientHandler gch = new GameClientHandler(clientSocket,p);
                    clients.add(gch);
                    GameState.getGM().addPlayer(p);
                    gch.start();
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**

     Broadcasts a message to all connected clients.
     @param message The message to be broadcasted.
     */
    public static void broadcastToClients(String message) {
        synchronized (clients) {
            for (GameClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }
    /**

     Removes a client from the list of connected clients.
     @param gameClientHandler The GameClientHandler representing the client to be removed.
     */
    public static void removeClient(GameClientHandler gameClientHandler) {
        synchronized (clients) {
            clients.remove(gameClientHandler);
        }
    }

}


