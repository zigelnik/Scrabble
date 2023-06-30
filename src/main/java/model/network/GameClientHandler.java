package model.network;


import model.concrete.Player;

import java.io.*;
import java.net.Socket;

/**

 The GameClientHandler class represents a client handler for the game server.

 It manages the communication with a specific client and handles incoming messages.
 */

public class GameClientHandler extends Thread {
    public Socket socket;
    static private BufferedReader readFromClient;
    private PrintWriter writeToClient;
    String stringWord;
    String query;
    public Player player;
    public Object locker = new Object();


    /**

     Constructs a GameClientHandler object with the specified socket and player.
     @param s The socket associated with the client.
     @param p The Player object representing the client.
     */
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

    /**

     Runs the client handler thread.

     It listens for incoming messages from the client and handles them accordingly.
     */

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

    /**

     Sends a message to the connected client.
     @param message The message to be sent.
     */
    public void sendMessage(String message) {
        writeToClient.println(message);
    }

    /**

     Prompts the client to enter a query and returns the entered query.
     @return The entered query as a string.
     */
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




