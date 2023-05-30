package model.network;
import model.concrete.GameState;

import java.io.*;
import java.net.*;
import java.util.Scanner;


// Client class
public class Client {
    String ip;
    int port;
    Socket socket;
    Scanner consoleReader;
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

            consoleReader = new Scanner(System.in);
            readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeToServer = new PrintWriter(socket.getOutputStream(), true);


            // Receiving messages from the server
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = readFromServer.readLine()) != null) {
                        System.out.println( message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // Sending messages to the server
            String msg;
            while ((msg = consoleReader.nextLine()) != null) {
                writeToServer.println(msg);
            }

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


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Closing the client");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}