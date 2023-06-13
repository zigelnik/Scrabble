package model.network;
import javafx.application.Platform;
import javafx.scene.control.Label;
import model.concrete.GameState;
import model.concrete.Player;
import model.concrete.Tile;
import view.GamePage;
import view.Main;
import view.WaitingPage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


// Client class
public class Client extends Player {
    String ip;
    int port;
    String name;
    Socket socket;
    BufferedReader consoleReader;
    BufferedReader readFromServer;
    PrintWriter writeToServer;
    private final GamePage gp = GamePage.getGP();


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


            // receiving msg (another thread?)
                    String message;
                    while ((message = readFromServer.readLine()) != null)
                    {
                        if(message.equals("/start"))
                        {
                            Platform.runLater(()->
                                    gp.start(WaitingPage.theStage));

                        }
                        else if(message.equals("/initYourRack"))
                        {
                            Platform.runLater(()->{
                                //update to all the Clients their GUI playerRack
                                List<String> playerRack = new ArrayList<>();
                                for(Tile tmp: getPlayerHand()){
                                    char c = tmp.letter;
                                    playerRack.add(Character.toString(c));
                                }
                                gp.initPlayerRack(playerRack);
                            });
                        }
                     }



        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writeToServer.println(socket.getLocalAddress()+"has left");
                socket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}