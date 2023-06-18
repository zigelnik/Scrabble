package model.concrete;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Player {

    // data members ////////
    String playerName;
    int id;
    List<Tile> playerHand = Collections.synchronizedList(new ArrayList<>());
    int handSize; // physical size of tiles
    int sumScore;
    boolean isTurnOver;
    String acceptedQuery;

    ///////////////////////




    public Player(){
        this.id = 0;
        this.handSize = 7;
        this.sumScore = 0;
        isTurnOver = false;

    }


    // Setters
    public void setSumScore(int sumScore) {
        this.sumScore = sumScore;
    }
    public void setName(String name)
    {
        this.playerName = name;
    }

    public void setHandSize(int handSize) {
        this.handSize = handSize;
    }
    public void setPlayerHand(List<Tile> playerHand) {
        this.playerHand = playerHand;
    }


    // Getters
    public int getHandSize()
    {
        return handSize;
    }

    public List<Tile> getPlayerHand()
    {
        return this.playerHand;
    }

    public int getId() {return id;}

    public int getSumScore() {
        return sumScore;
    }
    public String getPlayerName() {
        return playerName;
    }

    public boolean handIsFull()
    {
        return playerHand.size() == 7;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && handSize == player.handSize && sumScore == player.sumScore && isTurnOver == player.isTurnOver  && Objects.equals(playerName, player.playerName) && Objects.equals(playerHand, player.playerHand);
    }

    @Override
    public int hashCode() {
        return Objects.hash( playerName, id, playerHand, handSize, sumScore, isTurnOver);
    }

    public List<String> convertTilesToStrings(List<Tile> tiles){
        List<String> tmpList = new ArrayList<>();
        for(Tile t : tiles){
            tmpList.add(String.valueOf(t.getLetter()));
        }
        return tmpList;
    }

    public List<Tile> StringToTiles(String str)
    {
        List<Tile> tiles = new ArrayList<>();
        for(char ch: str.toCharArray())
        {
            if(ch != ',')
            {
                tiles.add(Tile.Bag.getBag().getTile(ch));
            }
        }
        return  tiles;
    }




}