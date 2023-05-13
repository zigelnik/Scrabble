package model;

import model.logic.MyServer;
import java.util.ArrayList;
import java.util.List;

public class ScrabbleFacade {
    GameState gs;
    List<Player> playersList;

    ScrabbleFacade(){
        this.playersList = new ArrayList<>();
        this.gs = new GameState(playersList);
    }


//     * Hosts a new game on a specified port
    void hostGame(String port){
        // creating Host as player
        playersList.add(new Player());
        int portint = Integer.parseInt(port);
        MyServer server = new MyServer(portint, new Main.ClientHandler1());
        boolean ok = true;
        int c = Thread.activeCount();
        server.start(); // runs in the background
        try {
            //client1(portint);
        } catch (Exception e) {
            System.out.println("some exception was thrown while testing your server, cannot continue the test (-100)");
            ok = false;
        }
        server.close();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        if (Thread.activeCount() != c) {
            System.out.println("you have a thread open after calling close method (-100)");
            ok = false;
        }
    }


//     * Joins an existing game hosted in a specified port and address.
    void joinGame(String ip, String port){
        // creating Guest as player
        playersList.add(new Player());

        // to do: if there isnt any host, dont connect
    }



    void disconnect(){

    }

}
