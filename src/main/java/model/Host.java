package model;

import model.concrete.GameState;
import model.concrete.Player;
import model.network.GameServer;
import model.network.QueryServer;

public class Host extends Player {

    public GameServer gameServer;
    public QueryServer queryServer;
    public GameState gameState;

    public Host(String name,int port)
    {
        this.gameServer = new GameServer(port);
        setName(name);
        this.gameState =GameState.getGM();
    }

}
