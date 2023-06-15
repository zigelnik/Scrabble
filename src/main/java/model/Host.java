package model;

import model.concrete.GameState;
import model.concrete.Player;
import model.network.GameServer;
import model.network.QueryServer;

public class Host extends Player {

    public GameServer gameServer;
    public QueryServer queryServer;
    public GameState gameState = GameState.getGM();

    public Host(String name)
    {
        setName(name);
    }

}
