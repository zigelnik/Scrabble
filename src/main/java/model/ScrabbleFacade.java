package model;

import model.logic.MyServer;

<<<<<<< HEAD
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
=======
public interface ScrabbleFacade {


    void hostGame(int port);

    void joinGame(String ip, int port );
>>>>>>> Zigel



    void addPlayer(Player player);

    void disconnect();


}
