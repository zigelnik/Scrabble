package model;

import model.logic.MyServer;

import java.util.List;
import java.util.Observable;

public class Model extends Observable implements ScrabbleFacade {

    MyServer hostServer;
    GameState gameState; // controls all the objects of the game, whenever something changes the game state updates.
    GuestPlayer client;
    List<Player> playerList;

    public Model() {
        gameState = GameState.getGameState();
    }


    //TODO: where the player setting again his query after it placed?
    // should be in the main?
    public void initGame(){
        playerList = gameState.getPlayersList();

        //TODO randomize a player to start the game method -> after that, initPack with 7 tiles for each player

        while(!gameState.isGameOver)
        {
//            Player p = new Player();
//            int playerInd = p.id;
            // TODO:method who runs on the turn each time

            if (getHost() != null)
            {
              //  ((HostPlayer) getHost()).tmpDictionaryLegal(p.getQuery().toString());
              //  p.makeMove(p.getQuery());
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
        client = new GuestPlayer(ip,port);
        client.start();
        addPlayer(client);

    }



    @Override
    public void addPlayer(Player player) {
        gameState.addPlayer(player);
    }

    @Override
    public void disconnect() {
        hostServer.close();
        //TODO: printing to view of player is disconnected
        //TODO: make sure the all the servers and threads are closed
    }
}
