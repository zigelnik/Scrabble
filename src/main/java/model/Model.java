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
    public void initGame(){
        int currPlayerInd = 1;
        List<Player> playerList = GameState.setTurns(); // players turns by their index in playerList
        playerList.stream().forEach((p)->p.initHand());
       // hostPlayer.loadBooks();
        while(!GameState.isGameOver)
        {
            for(Player player: GameState.playersList)
            {
                while(!player.isTurnOver)
                {
                   player.isTurnOver =  legalMove(player);

                }
                player.isTurnOver = false; // returning so next round the player can play again his turn.

                currPlayerInd = ((currPlayerInd+1) % playerList.size());

                // do we need to get the winner as object or change the isWinner to void?
                Player winner = GameState.isWinner();
            }

        }


       // System.out.println(hp.tmpDictionaryLegal("Q,mobydick.txt,"+"TOKEN"));
    }

    //Getting the host from players list, if null: Host not found
    public Player getHost(){
        for( Player p: GameState.getPlayersList()) {
            if (p.getClass().equals(HostPlayer.class)) {
                return p;
            }
        }
        return null;
    }

    public boolean legalMove(Player player)
    {
        int score=0;
        /*
        *
        *             //this is the player that his turn now
            if (getHost() != null)
            {
                //TODO: make the clients always trying to attend to the host, only when its clear the host
                //will coneect him else he will get message : its not you turn
                //TODO: put all of this in loop, what if he mistakes? it his turn again.
                ((HostPlayer) getHost()).tmpDictionaryLegal(tmpPlayer.getWordQuery().toString());
                tmpPlayer.makeMove(tmpPlayer.getWordQuery());
            }
            * */
        String msg=null;
        if(player.getClass().equals(GuestPlayer.class))
        {
            ((GuestPlayer) player).sendQuery();
            /* client interacting with bookscrabble handler */
             msg = GameClientHandler.getMessageQuery();

        }

            boolean validQuery;
            validQuery = hostPlayer.tmpDictionaryLegal(msg);
            if(validQuery)
            {
              score=  player.makeMove(hostPlayer.convertStrToWord(msg));

                return score != 0;
            }

        return false; //set to change
    }

    @Override
    public void hostGame(int port) {
        hostServer= new GameServer(port);
        hostPlayer = new HostPlayer();
        addPlayer(hostPlayer);
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
