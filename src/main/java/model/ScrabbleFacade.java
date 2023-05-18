package model;

import java.util.ArrayList;
import java.util.List;

public class ScrabbleFacade {

    GameState gs;
    List<Player> playerList;

    ScrabbleFacade(){
        this.playerList = new ArrayList<>();
        this.gs = new GameState(playerList);
    }

    void hostGame(int port){
//        playerList.add(new Player());
        GameServer game = new GameServer(port);
        game.start();
    }


    void joinGame(String ip, int port ){
//        playerList.add(new Player());
        GameClient client = new GameClient(ip,port);
        client.start();
    }


    void initGame(){
        playerList.stream().forEach((p)->p.initPack());
        //TODO: running the dictionary server
    }

    // there is dictionaryLegal method from patam1 , return always True.
    boolean tmpDictionaryLeagal(){
        //TODO: with given word we will open new thread to dictionaryServer
        //TODO: check with dm if the word is legal , return true or false
        //TODO: closing the tread, this method will run each time a player want to make move
        return true;
    }

    void disconnect(){
        //TODO:prinying to view of player is disconnected
        //TODO: make sure the all the servers and threads are closed


    }

}
