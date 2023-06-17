package model.network;
import javafx.application.Platform;
import model.Model;
import model.concrete.Player;
import view.GamePage;
import view.View;
import view.WaitingPage;
import view_model.ViewModel;

import java.io.*;
import java.net.*;


// Client class
public class Client  {
    String ip;
    int port;
    String name;
    Socket socket;
    BufferedReader consoleReader;
    BufferedReader readFromServer;
    PrintWriter writeToServer;
    private GamePage gp = GamePage.getGP();
    private WaitingPage wp = new WaitingPage();
    public ViewModel vm = ViewModel.getViewModel();
    public View v = View.getView();
    public Player p  = new Player();

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

       new Thread(()->
       {
           String message;
            try
            {
                while ((message = readFromServer.readLine()) != null)
                {
                    if (message.equals("/query"))
                    {
                        message = readFromServer.readLine();
                        p.setPlayerHand(p.StringToTiles(message));
                        Model.getModel().updatePlayerValues(p.getSumScore(), p.convertTilesToStrings(p.getPlayerHand()),p.getId());
                        System.out.println(message);
                    }

                     else if (message.equals("/start"))
                    {
                        Platform.runLater(() ->
                        {
                            gp.start(WaitingPage.theStage);
                            Model.getModel().updatePlayerValues(p.getSumScore(), p.convertTilesToStrings(p.getPlayerHand()),p.getId());
                        });

                    }
                     else if(message.equals("/update")){
                        message = readFromServer.readLine();
                        p.setPlayerHand(p.StringToTiles(message));
                        p.setSumScore(Integer.parseInt(readFromServer.readLine()));
                        Model.getModel().updatePlayerValues(p.getSumScore(), p.convertTilesToStrings(p.getPlayerHand()),p.getId());
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();
            // receiving msg (another thread?)

            // sending msg
            String message;
            while ((message = consoleReader.readLine()) != null) {
                writeToServer.println(message);
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