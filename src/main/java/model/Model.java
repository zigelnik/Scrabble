package model;

import java.util.*;

import model.network.Client;
import model.network.GameServer;


public class Model extends Observable implements Facade {
    public Host host;
    public Client client;
    private String playerQuery;
    public int playerId;
    Map<Integer,List<String>> playersHandMap = Collections.synchronizedMap(new HashMap<>());
    Map<Integer,Integer> playersScoreMap = Collections.synchronizedMap(new HashMap<>());



    @Override
    public void hostGame(int port, String name)
    {
        host = new Host(name,port);
        host.gameState.addPlayer(host);
        host.gameServer.start();
    }

    @Override
    public void joinGame(String ip, int port,String name) {
        client = new Client(ip, port,name);
        client.start();
    }


    @Override
    public void disconnect() {

    }

    // Updates-for player Score and playerHand (usages: GameState->initHands | HostPlayer->initGame)
    public void updatePlayerValues(int playerScore, List<String> playerHand, int id) {
        playerId = id;
        playersHandMap.put(playerId,playerHand);
        playersScoreMap.put(playerId,playerScore);

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

    private  static class ModelHolder{ public static final Model m = new Model();}
    public static Model getModel() {return ModelHolder.m;}
    public Map<Integer, List<String>> getPlayersHandMap() {
        return playersHandMap;
    }
    public Map<Integer, Integer> getPlayersScoreMap() {
        return playersScoreMap;
    }

}
