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
        playerList.add(new Player());
        GameServer game = new GameServer(port);
        game.start();
    }


    void joinGame(String ip, int port ){
        playerList.add(new Player());
        GameClient client = new GameClient(ip,port);
        client.start();
    }


    void disconnect(){

    }

}
