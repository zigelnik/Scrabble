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
    private GamePage gp = GamePage.getGP();
    private GameState gameState;


    public Client(String ip, int port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;
    }

    public void start()
    {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to server.");

            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeToServer = new PrintWriter(socket.getOutputStream(), true);

            writeToServer.println(name+" has connected from: "+socket.getLocalSocketAddress());

            // receiving msg (another thread?)
                Thread t = new Thread(()->
                {
                    String message;
                    try {
                        while ((message = readFromServer.readLine()) != null)
                            {
                                if (message.equals("/start"))
                                {
                                    Platform.runLater(() ->
                                            gp.start(WaitingPage.theStage));
                                } else {
                                    System.out.println(message);
                                }
                            }
                        } catch (IOException e) { e.printStackTrace();  }
                });
                t.start();

                // sending msg
                String message;
                while ((message = consoleReader.readLine()) != null) {
                    writeToServer.println(message);
                }

             } catch (IOException e) {
            System.out.println("There was a problem connecting to the server");       }

        finally {
            try {
                writeToServer.println(name+"has left the server.");
                writeToServer.close();
                readFromServer.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}