package model;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

// Server class
public class GameServer {
    int port;
    private static final int MAX_CLIENTS = 3;
    private static List<ClientHandler> clients = new ArrayList<>();
    public GameServer(int port) {
        this.port = port;
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hostThread.start();

            while (true) {
                Socket clientSocket = serverSocket.accept();

                if (clients.size() < MAX_CLIENTS) {
                    System.out.println("New client connected: " + clientSocket);

                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    clients.add(clientHandler);
                    clientHandler.start();
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
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }

    public static void removeClient(ClientHandler clientHandler) {
        synchronized (clients) {
            clients.remove(clientHandler);
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket) {
        try {
            clientSocket = socket;
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String clientName = reader.readLine();
            System.out.println("Client name set: " + clientName);

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received message from client " + clientName + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                clientSocket.close();
                GameServer.removeClient(this);
                GameServer.broadcastToClients("Client " + clientSocket + " has left the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}
