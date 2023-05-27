package model;


import java.util.Observable;

public class Model extends Observable implements Facade {

    GameServer hostServer;

    @Override
    public void hostGame(int port) {
        hostServer= new GameServer(port);
        hostServer.start();
    }
    @Override
    public void joinGame(String ip, int port) {
        Client client = new Client(ip,port);
        client.start();
    }

    @Override
    public void disconnect() {
       // hostServer.close();
        //TODO: printing to view of player is disconnected
        //TODO: make sure the all the servers and threads are closed
    }
}
