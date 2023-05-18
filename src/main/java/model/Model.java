package model;

import model.logic.BookScrabbleHandler;
import model.logic.MyServer;

import java.util.Observable;

public class Model extends Observable implements ScrabbleFacade {

    MyServer hostServer;
    GameState gameState;
    GameClient client;


    public Model() {
        this.gameState = new GameState();
    }

    public void initGame(){
        HostPlayer hp = new HostPlayer();
        System.out.println(hp.tmpDictionaryLegal("Q,mobydick.txt,mobydick.txt,"+"Stewart",true));
    }



    @Override
    public void hostGame(int port) {
        //model.gameState.addPlayer(new Player(?name?));
        hostServer= new MyServer(port, new GameClientHandler());
        hostServer.start();
    }

    @Override
    public void joinGame(String ip, int port) {
        // model.gameState.addPlayer(new Player());
       client = new GameClient(ip,port);
        client.start();
    }

    @Override
    public void addPlayer(String name) {
        //TODO: where should we put this method of creating players.
    }

    @Override
    public void disconnect() {
        //TODO: printing to view of player is disconnected
        //TODO: make sure the all the servers and threads are closed
    }
}
