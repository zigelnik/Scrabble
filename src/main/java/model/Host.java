package model;

import model.concrete.GameState;
import model.concrete.Player;
import model.network.GameServer;
import model.network.QueryServer;
/**

 The Host class represents the host player in the game. It extends the Player class and provides additional functionality for hosting a game.
 */
public class Host extends Player {

    public GameServer gameServer;
    public QueryServer queryServer;
    public GameState gameState;

    /**
     * Constructs a new Host object with the specified name and port.
     *
     * @param name The name of the host player.
     * @param port The port number for the game server.
     */

    public Host(String name,int port)
    {
        this.gameServer = new GameServer(port);
        setName(name);
        this.gameState =GameState.getGM();
    }

}
