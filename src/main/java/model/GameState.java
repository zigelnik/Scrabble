package model;

import model.concrete.Board;
import model.concrete.Tile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Player> setTurns(){
        //extracting randomly tile for each player, setting is id, returning to bag
        for(Player p : playersList){
            Tile tmpTile =  gameCash.getRand();
            p.id = tmpTile.score;
            gameCash.put(tmpTile);

        }
        // sorting the list from big id to small id with sorting & reversing the order
        playersList = playersList.stream().sorted(Comparator.comparingInt(Player::getId).reversed())
                .collect(Collectors.toList());

        //first player at list is now the playing first randomly
        return playersList;
    }


    public void addPlayer(Player player)
    {
       playersList.add(new Player());
    }

    public Player isWinner(){
        int max = 0;
        Player tmpPlayer = null;
        //Winner: when the tiles bag is empty and the winner finished his pack
        if(gameCash.getTilesCounter() == 0){
            for(Player p : playersList){
                if(max < p.sumScore && p.packSize == 0){
                    max = p.sumScore;
                    tmpPlayer =  p;
                }
            }
            isGameOver = true;
            return tmpPlayer;
        }
        return null;
    }

    public static GameState getGameState() {
        if (gameStateInstance == null)
            gameStateInstance = new GameState();

        return gameStateInstance;
    }

}
