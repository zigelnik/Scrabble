package model.network;
import javafx.application.Platform;
import model.concrete.GameState;
import view.GamePage;
import view.Main;
import view.WaitingPage;

import java.io.*;
import java.net.*;


// Client class
public class Client {
    String ip;
    int port;
    String name;
    Socket socket;
    BufferedReader consoleReader;
    BufferedReader readFromServer;
    PrintWriter writeToServer;
    private  GamePage gp = GamePage.getGP();
    private GameState gameState;


    public Client(String ip, int port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;
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
                        if(message.equals("/start"))
                        {
                            Platform.runLater(()->
                                    gp.start(WaitingPage.theStage));
                        }
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

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writeToServer.println(socket.getInetAddress()+"has left");
                socket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}