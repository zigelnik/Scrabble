package model.network;


import model.concrete.Player;

import java.io.*;
import java.net.Socket;

public class GameClientHandler extends Thread {
    public Socket socket;
    static private BufferedReader readFromClient;
    private PrintWriter writeToClient;
    String stringWord;
    String query;
    public Player player;
    public Object locker = new Object();

    public GameClientHandler(Socket s, Player p) {
        try {
            player =p;
            socket = s;
            readFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeToClient = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void run() {
        try {

            System.out.println("New client connected from: "+ socket.getLocalSocketAddress());

            String message;
            while ((message = readFromClient.readLine()) != null) {
                if(message.equals("/turn")){
                    message = readFromClient.readLine();
                    query = message;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                readFromClient.close();
                writeToClient.close();
                socket.close();
                GameServer.removeClient(this);
                GameServer.broadcastToClients("Client " + socket + " has left the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendMessage(String message) {
        writeToClient.println(message);
    }

    public  String getMessageQuery()
    {
        writeToClient.println("Enter you query: ");
        try {
            stringWord = readFromClient.readLine();
            writeToClient.println(stringWord);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringWord;
    }

    public String getQuery() {
        return query;
    }

}




