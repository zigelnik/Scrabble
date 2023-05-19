package model;

import model.logic.MyServer;

import java.util.List;
import java.util.Observable;

public class Model extends Observable implements ScrabbleFacade {

    MyServer hostServer;
    GameState gameState; // controls all the objects of the game, whenever something changes the game state updates.
    GameClient client;
    List<Player> playerList;

    public Model() {
        gameState = GameState.getGameState();
    }

    public void initGame(){
        playerList = gameState.getPlayersList();
        while(!gameState.isGameOver)
        {
            if (getHost() != null)
            {
                ((HostPlayer) getHost()).tmpDictionaryLegal("");
            }

        }

        HostPlayer hp = new HostPlayer();

        System.out.println(hp.tmpDictionaryLegal("Q,mobydick.txt,"+"TOKEN"));
    }

    //Getting the host from players list, if null: Host not found
    public Player getHost(){
        for( Player p: playerList) {
            if (p.getClass().equals(HostPlayer.class)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void hostGame(int port) {
        hostServer= new MyServer(port, new GameClientHandler());
        hostServer.start();
        addPlayer(new HostPlayer());

    }

    @Override
    public void joinGame(String ip, int port) {
        client = new GameClient(ip,port);
        client.start();
        addPlayer(new GuestPlayer());

    }

    @Override
    public void addPlayer(Player player) {
        gameState.addPlayer(new Player());
    }

    @Override
    public void disconnect() {
        hostServer.close();
        //TODO: printing to view of player is disconnected
        //TODO: make sure the all the servers and threads are closed
    }
}
