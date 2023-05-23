package model;


import java.util.List;
import java.util.Observable;

public class Model extends Observable implements Facade {

    GameServer hostServer;
    HostPlayer hostPlayer;
    GameState gameState;
    public Model() {
             gameState= GameState.getGameState();
    }


    //TODO: where the player setting again his query after it placed?
    // should be in the main?


    //Getting the host from players list, if null: Host not found
    public Player getHost(){
        for( Player p: GameState.getPlayersList()) {
            if (p.getClass().equals(HostPlayer.class)) {
                return p;
            }
        }
        return null;
    }


    @Override
    public void hostGame(int port) {
       // hostPlayer = new HostPlayer();

        hostServer= new GameServer(port);
        addPlayer(hostServer.getHost());

        hostServer.start();

    }

    @Override
    public void joinGame(String ip, int port) {
        GuestPlayer client = new GuestPlayer(ip,port);
        addPlayer(client);

        client.start();

    }


    @Override
    public void addPlayer(Player player) {
        GameState.addPlayer(player);
    }

    @Override
    public void disconnect() {
       // hostServer.close();
        //TODO: printing to view of player is disconnected
        //TODO: make sure the all the servers and threads are closed
    }
}
