package model.network;

import model.concrete.GameState;
import model.concrete.HostPlayer;
import model.concrete.Player;
import model.logic.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameClientHandler implements ClientHandler {
    public static List<GameClientHandler> clients = new ArrayList<>();
    public static List<Player> playersList = new ArrayList<>();
    public static int clientCount =0;
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
        Player p = new Player();
        Scanner console = new Scanner(System.in);
        in = new Scanner(inFromclient);
        out = new PrintWriter(outToClient,true);

        out.println("please enter your name.");
        p.setName(in.nextLine());
        name = p.getPlayerName();
        playersList.add(p);
        System.out.println(p.getPlayerName()+" has connected to the server.");
        out.println("You have been connected to the server.");

        Thread t = new Thread(() -> {
            String message;
            try {
                while ((message = in.nextLine()) != null) {
                    System.out.println(name+": "+message);
                }
            } catch (NoSuchElementException e) {
                // Client has disconnected
                System.out.println(name + " has left.");
            }
        });
        t.start();

        String msg;
        while ((msg = console.nextLine()) != null) {
            GameClientHandler.broadcastToClients("Server: " + msg);
        }
    }

    @Override
    public void close() {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(name+" has left.");

            synchronized (clients)
            {

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

    public static List<GameClientHandler> getClients()
    {
        return clients;
    }
}



