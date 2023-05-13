package model;

import model.concrete.Board;
import model.concrete.Tile;

import java.util.List;

public class GameState {
    int curPlayerInd = 0;
    Tile.Bag gameCash;
    List<Player> playersList;
    Board board;

    //CTOR
    public GameState(List<Player> playersList) {
        this.board = Board.getBoard();
        this.gameCash = Tile.Bag.getBag();
        this.playersList = playersList;
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


    // Funcctions
    Player playerTurn(Player tmpTurn){
        // return the player that his id is next to the current player's id
        curPlayerInd = (curPlayerInd + 1) % playersList.size();
        return playersList.get(curPlayerInd);
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
            return tmpPlayer;
        }
        return null;
    }

}
