package model.network;

import javafx.stage.Stage;
import model.concrete.GameState;
import model.concrete.Player;
import view.GamePage;

import java.io.*;
import java.net.Socket;

public class GameClientHandler extends Thread {
    private Socket clientSocket;
    static private BufferedReader readFromClient;
    private PrintWriter writeToClient;
    public Player player;
    GamePage gp = new GamePage();
    String stringWord;

    public GameClientHandler(Socket socket, Player p) {
        try {
            player = p;
            clientSocket = socket;
            readFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writeToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {

            writeToClient.println("enter your name: ");
            player.setName(readFromClient.readLine());
            System.out.println("New client connected: " +player.getPlayerName()+" | From: "+ clientSocket.getInetAddress());

            String message;
            while ((message = readFromClient.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                readFromClient.close();
                writeToClient.close();
                clientSocket.close();
                GameServer.removeClient(this);
                GameServer.broadcastToClients("Client " + clientSocket + " has left the chat.");
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




    public void updateClientsState(GameState gameState)
    {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.writeObject(gameState);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initPlayersBaord(){
        gp.start(new Stage());
    }

}




