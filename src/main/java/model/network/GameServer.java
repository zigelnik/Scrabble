package model.network;

import model.concrete.GameState;
import model.concrete.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

// Server class
public class GameServer {
    int port;
    public volatile boolean stop = false;
    private static final int MAX_CLIENTS = 3;
    public static List<GameClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);

    public GameServer(int port) {
        this.port = port;

    }

    public static List<GameClientHandler> getClients() {
        return clients;
    }

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

    public static void broadcastToClients(String message) {
        synchronized (clients) {
            for (GameClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }

    public static void removeClient(GameClientHandler gameClientHandler) {
        synchronized (clients) {
            clients.remove(gameClientHandler);
        }
    }


    //GETTERS

}


