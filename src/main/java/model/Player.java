package model;

import model.concrete.Board;
import model.concrete.Tile;
import model.concrete.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Player {

        // data members ////////
    protected GameState gameState;
    String playerName;
    int id;
    List<Tile> playerHand;
    int handSize; // physical size of tiles
    int sumScore;
    static String wordQuery;
    boolean isTurnOver;
        ///////////////////////


    public Player(){
        this.id = 0;
        this.playerHand = new ArrayList<>();
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
    public GameState getGameState() {
        return gameState;
    }
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


}