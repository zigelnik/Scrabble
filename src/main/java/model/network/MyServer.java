package model.network;
import model.logic.ClientHandler;
import model.network.GameClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    private int port;
    private ClientHandler ch;
    private volatile boolean stop;
    private ServerSocket server;
    private static final int maxPlayers = 3;
    private static int numOfPlayers = 0;
    private ExecutorService executorService;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        executorService = Executors.newFixedThreadPool(maxPlayers);
    }

    public void start() {
        stop = false;
        try {
            server = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (!stop) {
                Socket clientSocket = server.accept();
                if (GameClientHandler.clientCount == maxPlayers) {
                    System.out.println("Too many players. Connection rejected.");
                    clientSocket.close();
                    continue;
                }

                // Create a new thread to handle the client connection
                executorService.execute(() -> {
                    try {
                        ch.handleClient(clientSocket.getInputStream(), clientSocket.getOutputStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public void close() {
        stop = true;
        ch.close();
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
