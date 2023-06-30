package model;

import java.util.*;

import model.network.Client;
import model.network.GameServer;
/**

 The Model class represents the model component of the game. It implements the Facade interface to provide high-level methods for controlling the game state.
 It is responsible for managing the game state, hosting and joining games, updating player values, and notifying observers of changes.
 */

public class Model extends Observable implements Facade {
    public Host host;
    public Client client;
    private String playerQuery;
    public int playerId;
    public String boardQuery;
    Map<Integer,List<String>> playersHandMap = Collections.synchronizedMap(new HashMap<>());
    Map<Integer,Integer> playersScoreMap = Collections.synchronizedMap(new HashMap<>());



    /**
     * Starts hosting a game with the specified port and name.
     *
     * @param port The port number for hosting the game.
     * @param name The name of the host.
     */
    @Override
    public void hostGame(int port, String name) {
        host = new Host(name, port);
        host.gameState.addPlayer(host);
        host.gameServer.start();
    }

    /**
     * Joins a game with the specified IP, port, and name.
     *
     * @param ip   The IP address of the host.
     * @param port The port number for joining the game.
     * @param name The name of the player joining the game.
     */
    @Override
    public void joinGame(String ip, int port, String name) {
        client = new Client(ip, port, name);
        client.start();
    }

    /**
     * Disconnects from the game.
     */
    @Override
    public void disconnect() {

    }

    /**
     * Updates the player values (score and hand) based on the received data.
     *
     * @param playerScore The score of the player.
     * @param playerHand  The hand of the player.
     * @param id          The ID of the player.
     */
    public void updatePlayerValues(int playerScore, List<String> playerHand, int id) {
        playerId = id;
        playersHandMap.put(playerId, playerHand);
        playersScoreMap.put(playerId, playerScore);

        setChanged();
        notifyObservers();
    }

    /**
     * Updates the board query.
     *
     * @param boardQuery The board query.
     */
    public void updateBoard(String boardQuery) {
        this.boardQuery = boardQuery;
        setChanged();
        notifyObservers();
    }

    /**
     * Updates the player query.
     *
     * @param msg The player query.
     */
    public void updateQuery(String msg) {
        this.playerQuery = msg;
        setChanged();
        notifyObservers();
    }

    /**
     * Retrieves the player query.
     *
     * @return The player query.
     */
    public String getPlayerQuery() {
        return playerQuery;
    }

    /**
     * Static holder class for the Model instance.
     */
    private static class ModelHolder {
        public static final Model m = new Model();
    }

    /**
     * Retrieves the Model instance.
     *
     * @return The Model instance.
     */
    public static Model getModel() {
        return ModelHolder.m;
    }

    /**
     * Retrieves the players' hand map.
     *
     * @return The players' hand map.
     */
    public Map<Integer, List<String>> getPlayersHandMap() {
        return playersHandMap;
    }

    /**
     * Retrieves the players' score map.
     *
     * @return The players' score map.
     */
    public Map<Integer, Integer> getPlayersScoreMap() {
        return playersScoreMap;
    }


}
