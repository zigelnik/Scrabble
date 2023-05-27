package model;


import java.util.Observable;

public class Model extends Observable implements Facade {

    GameServer hostServer;
    public Model() {
    }


    //TODO: where the player setting again his query after it placed?
    // should be in the main?




    @Override
    public void hostGame(int port) {
       // hostPlayer = new HostPlayer();

        hostServer= new GameServer(port);
       // addPlayer(new HostPlayer());

        hostServer.start();

    }

    @Override
    public void joinGame(String ip, int port) {
        Client client = new Client(ip,port);
      //  addPlayer(client);

        client.start();

    }
    


    @Override
    public void disconnect() {
       // hostServer.close();
        //TODO: printing to view of player is disconnected
        //TODO: make sure the all the servers and threads are closed
    }
}
