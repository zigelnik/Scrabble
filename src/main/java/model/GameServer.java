package model;
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
    public GameServer(int port) {
        this.port = port;
        hostPlayer = new HostPlayer();
    }
    public HostPlayer getHost()
    {
        return hostPlayer;
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

                    GameClientHandler gch = new GameClientHandler(clientSocket);
                    clients.add(gch);

                    System.out.println("New client connected: " +gch.getClientName()+" | From: "+ clientSocket);

                    gch.start();
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

    public static void removeClient(GameClientHandler gameClientHandler) {
        synchronized (clients) {
            clients.remove(gameClientHandler);
        }
    }
}


