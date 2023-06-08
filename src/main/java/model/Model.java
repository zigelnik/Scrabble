package model;


import java.util.Observable;
import model.concrete.GameState;
import model.network.Client;
import model.network.GameServer;



public class Model extends Observable implements Facade {
    GameServer hostServer;
    GameState gameState;


    @Override
        public void hostGame(int port,String name) {
            gameState = GameState.getGameState();
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

    public GameServer getHostServer() {return hostServer;}

    public GameState getGameState() {
        return gameState;
    }


    private  static class ModelHolder{ public static final Model m = new Model();}
    public static Model getModel() {return ModelHolder.m;}

}
