package model;

import model.concrete.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class GameClientHandler extends Thread {
    private Socket clientSocket;
    static private BufferedReader reader;
    private PrintWriter writer;
    Player player;
    String clientName;

    String stringWord;

    public GameClientHandler(Socket socket, Player p) {
        try {
            player = p;
            clientSocket = socket;
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
//             clientName = reader.readLine();
//            System.out.println("Client name set: " + clientName);
//            player.setName(clientName);

            String message;
//            if(message.equals("/query"))
//            {
//                stringWord = message;
//                System.out.println("query: "+stringWord);
//            }
            while ((message = reader.readLine()) != null) {
                switch (message)
                {
                    case "/query":
                    {
                        System.out.println("Enter you query: ");
                        break;
                    }
                    default:
                        System.out.println("Received message from client " + clientName + ": " + message);
                        break;
                }
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

    public  String getMessageQuery()
    {
        System.out.println("Enter you query: ");
        try {
            stringWord = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringWord;
    }

    public String getClientName()
    {
        return this.clientName;
    }


}




