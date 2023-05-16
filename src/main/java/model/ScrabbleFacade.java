package model;

public class ScrabbleFacade {

    Model model;


    ScrabbleFacade(){
            model = new Model();
    }

    void hostGame(int port){
        //model.gameState.addPlayer(new Player(?name?));
        GameServer game = new GameServer(port);
        game.start();
    }

    void joinGame(String ip, int port ){
       // model.gameState.addPlayer(new Player());
        GameClient client = new GameClient(ip,port);
        client.start();
    }

    //TODO: where should we put this method of creating players.
    void addPlayer(String name)
    {
        model.gameState.addPlayer(name);

    }
    void disconnect(){
        //TODO:prinying to view of player is disconnected
        //TODO: make sure the all the servers and threads are closed


    }

}
