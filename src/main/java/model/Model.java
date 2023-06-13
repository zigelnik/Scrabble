package model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import model.concrete.Tile;
import model.network.Client;
import model.network.GameClientHandler;
import model.network.GameServer;
import model.network.QueryServer;


public class Model extends Observable implements Facade {

    GameServer hostServer;
    int playerScore;
    List<String> playerHand = new ArrayList<>();
    String playerQuery = new String();



    @Override
    public void hostGame(int port,String name) {
        hostServer = new GameServer(port,name);
        hostServer.start();
    }

    @Override
    public void joinGame(String ip, int port,String name) {
        Client client = new Client(ip, port,name);
        client.start();
    }



    @Override
    public void disconnect() {

    }

    // Updates-for player Score and playerHand (usages: GameState->initHands | HostPlayer->initGame)
    public void updatePlayerVals(int playerScore,List<String> playerHand) {
        this.playerScore = playerScore;
        this.playerHand = playerHand;
        setChanged();
        notifyObservers();
    }

    public void updateQuery(String msg){
        this.playerQuery = msg;
        setChanged();
        notifyObservers();
    }

    //Getters
    public String getPlayerQuery() {
        return playerQuery;
    }
    public List<String> getPlayerHand() {
        return playerHand;
    }
    public int getPlayerScore() {
        return playerScore;
    }
    public GameServer getHostServer() {return hostServer;}
    private  static class ModelHolder{ public static final Model m = new Model();}
    public static Model getModel() {return ModelHolder.m;}

}
