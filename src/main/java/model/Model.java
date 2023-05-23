package model;

import model.logic.MyServer;

import java.util.List;
import java.util.Observable;

public class Model extends Observable implements ScrabbleFacade {

    MyServer hostServer;
    GuestPlayer client;
    List<Player> playerList;

    public Model() {

    }


    //TODO: where the player setting again his query after it placed?
    // should be in the main?
    public void initGame(){
        int ind = 0;
        playerList = GameState.setTurns(); // players turns by their index in playerList
        playerList.stream().forEach((p)->p.initPack());

        while(!GameState.isGameOver)
        {
            //this is the player that his turn now
            Player tmpPlayer = playerList.get(ind);
            if (getHost() != null)
            {
                //TODO: make the clients always trying to attend to the host, only when its clear the host
                //will coneect him else he will get message : its not you turn
                //TODO: put all of this in loop, what if he mistakes? it his turn again.
                ((HostPlayer) getHost()).tmpDictionaryLegal(tmpPlayer.getQuery().toString());
                tmpPlayer.makeMove(tmpPlayer.getQuery());
            }

            ind = ((ind+1) % playerList.size());
            Player winner = GameState.isWinner();
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
        GameState.addPlayer(player);
    }

    @Override
    public void disconnect() {
        hostServer.close();
        //TODO: printing to view of player is disconnected
        //TODO: make sure the all the servers and threads are closed
    }
}
