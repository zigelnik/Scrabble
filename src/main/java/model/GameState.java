package model;

import model.concrete.Board;
import model.concrete.Tile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public  class GameState {
    int curPlayerInd = 0;
    static Tile.Bag bag;
    static List<Player> playersList;
    static Board board;
    static boolean isGameOver;
    private static GameState gameStateInstance = null;

    //CTOR
    private GameState() {
        GameState.board = Board.getBoard();
        GameState.bag = Tile.Bag.getBag();
        GameState.playersList = new ArrayList<>();
        GameState.isGameOver = false;
    }

    //Getters
    public static Tile.Bag getBag() {
        return bag;
    }

    public static List<Player> getPlayersList() {
        return playersList;
    }

    public static Board getBoard() {
        return board;
    }

    public static boolean getIsGameOver(){return isGameOver;}


    // Functions
    public static List<Player> setTurns(){
        //extracting randomly tile for each player, setting is id, returning to bag
        int id = 1;

        System.out.println("inside set turns");
        for(Player p : playersList){
            Tile tempTile = bag.getRand();
            p.id = tempTile.score;
            bag.put(tempTile);
        }
        // sorting the list from big id to small id with sorting & reversing the order
        playersList = playersList.stream().sorted(Comparator.comparingInt(Player::getId).reversed())
                .collect(Collectors.toList());

        for(Player p : playersList)
        {
            p.id = id;
            id++;
        }
        //first player at list is now playing first randomly
        return playersList;
    }


    public static void addPlayer(Player player)
    {
        playersList.add(new Player());
    }

    public static Player isWinner(){
        int max = 0;
        Player tmpPlayer = null;
        //Winner: when the tiles bag is empty and the winner finished his pack
        if(bag.getTilesCounter() == 0){
            for(Player p : playersList){
                if(max < p.sumScore && p.handSize == 0){
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
