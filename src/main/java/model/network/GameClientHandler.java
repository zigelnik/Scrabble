package model.network;

import model.concrete.GameState;
import model.concrete.HostPlayer;
import model.concrete.Player;
import model.logic.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameClientHandler implements ClientHandler {
    public static List<GameClientHandler> clients = new ArrayList<>();
    public static List<Player> playersList = new ArrayList<>();
    private static int clientCount =0;
    private HostPlayer hostPlayer;
    Scanner in;
    PrintWriter out;
    String stringWord;
    String name;


    public GameClientHandler() {
        hostPlayer = new HostPlayer();
    }

//    public void run() {
//        try {
//
//            writeToClient.println("enter your name: ");
//            player.setName(readFromClient.readLine());
//            System.out.println("New client connected: " +player.getPlayerName()+" | From: "+ clientSocket.getInetAddress());
//
//            String message;
//            while ((message = readFromClient.readLine()) != null) {
//                System.out.println(message);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                readFromClient.close();
//                writeToClient.close();
//                clientSocket.close();
//                GameServer.removeClient(this);
//                GameServer.broadcastToClients("Client " + clientSocket + " has left the chat.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }



    public  String getMessageQuery()
    {
        out.println("Enter you query: ");
        try {
            stringWord = in.nextLine();
            out.println(stringWord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stringWord;
    }


//    public void updateClientsState(GameState gameState)
//    {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//            outputStream.writeObject(gameState);
//            outputStream.flush();
//            outputStream.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        synchronized (clients)
        {
            clients.add(this);
            clientCount++;
        }

        Scanner console = new Scanner(new InputStreamReader(System.in));
        in = new Scanner(inFromclient);
        out = new PrintWriter(outToClient,true);

        String message;
        while ((message = console.nextLine()) != null) {
            if(message.equals("/start"))
                hostPlayer.initGame();
            else {
                out.println("Server: "+message);
            }
        }

        try {
            outToClient.flush();
        } catch (IOException e) {
            System.out.println("error flush in gch");
        }
    }

    public void setPlayerName(Player p, String pName) {
        out.println("please enter your name:");
        p.setName(pName);
        name = pName;
        playersList.add(p);
        System.out.println(p.getPlayerName()+" has connected to the server.");
    }

    @Override
    public void close() {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            synchronized (clients)
            {
                System.out.println(name+"has left.");

                clients.remove(this);
                clientCount--;
            }
        }

    }

    public  void sendMessage(String message) {
        out.println(message);
    }
    public static void broadcastToClients(String message) {
        synchronized (clients) {
            for (GameClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }

    // Getter's
    public static int getClientCount() {return clientCount;}
    public HostPlayer getHostPlayer() {return hostPlayer;}
    public static List<GameClientHandler> getClients()
    {
        return clients;
    }
}



