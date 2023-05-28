package model.network;
import model.concrete.GameState;

import java.io.*;
import java.net.*;

//TODO: why we need GameClient? why just not consider GuestPlayer as GameClinet and just open the sovket overthere?

// Client class
public class Client {
    String ip;
    int port;
    Socket socket;
    BufferedReader consoleReader;
    BufferedReader readFromServer;
    PrintWriter writeToServer;

    private GameState gameState;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start() {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to server.");

            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeToServer = new PrintWriter(socket.getOutputStream(), true);


            // receiving msg
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = readFromServer.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // --- should the client get a game state as object input stream and deal with it? ----
//            Thread stateThread = new Thread(() -> {
//                try {
//                    System.out.println("reading objects in client");
//                    ObjectInputStream inputStream= new ObjectInputStream(socket.getInputStream());
//                    gameState = (GameState) inputStream.readObject();
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//            stateThread.start();
//               -------------------------------------------------------

            // sending msg
            String message;
            while ((message = consoleReader.readLine()) != null) {
                writeToServer.println(message);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}