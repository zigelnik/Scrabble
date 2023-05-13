package model;

import model.logic.MyServer;

import java.util.ArrayList;
import java.util.List;

public class ScrabbleFacade {

    GameState gs;
    List<String> listenersList;

    ScrabbleFacade(){
        this.listenersList = new ArrayList<>();
        this.gs = new GameState();
    }

    void hostGame(int port){
        GameServer game = new GameServer(port);
        game.start();
    }


    void joinGame(String ip, int port ){
        GameClient client = new GameClient(ip,port);
        client.start();
    }

    public void getGameState(){
        gs.getScore(new Player());
        gs.getBoardState();
    }

    void disconnect(){

    }

}
