package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class GameClientHandler extends Thread {
    private Socket clientSocket;
    static private BufferedReader reader;
    private PrintWriter writer;

    public GameClientHandler(Socket socket) {
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

    public static String getMessageQuery()
    {
        String message;
        try {
            if ((message = reader.readLine()) != null) {
                System.out.println("query from client: "+message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return message;
    }
}