package model;

import model.concrete.Board;
import model.concrete.Tile;

import java.util.ArrayList;
import java.util.List;

public  class GameState {
    int curPlayerInd = 0;
    Tile.Bag gameCash;
    List<Player> playersList;
    Board board;
    boolean isGameOver;
    private static GameState gameStateInstance = null;

    //CTOR
    private GameState() {
        this.board = Board.getBoard();
        this.gameCash = Tile.Bag.getBag();
        this.playersList = new ArrayList<>();
        this.isGameOver = false;
    }

    //Getters
    public Tile.Bag getGameCash() {
        return gameCash;
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public Board getBoard() {
        return board;
    }

    public boolean getIsGameOver(){return isGameOver;}


    // Functions
    Player playerTurn(Player tmpTurn){
        // return the player that his id is next to the current player's id
        curPlayerInd = (curPlayerInd + 1) % playersList.size();
        return playersList.get(curPlayerInd);
    }

    public void addPlayer(Player player)
    {
       playersList.add(new Player());
    }

    Player isGameOver(){
        int max = 0;
        Player tmpPlayer = null;
        if(gameCash.getTilesCounter() == 0){
            for(Player p : playersList){
                if(max < p.sumScore){
                    max = p.sumScore;
                    tmpPlayer =  p;
                }
            }
            isGameOver = true;
            return tmpPlayer;
        }
        return null;
    }

        public void initPack()
        {       playersList.stream().forEach((p)->p.initPack());

        }
    public static GameState getGameState() {
        if (gameStateInstance == null)
            gameStateInstance = new GameState();

        return gameStateInstance;
    }

}

//TODO: checking first Tile for each player to set id for turns