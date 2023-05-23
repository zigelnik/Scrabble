package model;

import model.concrete.Tile;
import model.concrete.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    String playerName;
    int id;
    List<Tile> playerHand;
    int handSize; // physical size of tiles
    int sumScore;
    String wordQuery;

boolean isTurnOver;

    public Player(){
        this.id = 0;
        this.playerHand = new ArrayList<>();
        this.handSize = 7;
        this.sumScore = 0;
        isTurnOver = false;
    }

    public int makeMove(Word w){
        // if makeMove fails this integer will stay 0.
        int tmpMoveScore = 0;

        // if tiles are over
        if(handSize == 0){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player wants to place a word with not enough tiles
        else if(w.getTiles().length > handSize){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player don't have all the tiles for the word
        else if(!isContain(w)){
            System.out.println("Not all word tiles are existed");
            return tmpMoveScore;
        }
        tmpMoveScore += GameState.getBoard().tryPlaceWord(w); // placing the word at the same board
        // after all checks,decline the words size from pack and init pack back to 7.
        if(tmpMoveScore != 0){
            handSize -= w.getTiles().length;
            initHandAfterMove(w);
        }
        sumScore += tmpMoveScore;
        //if tmpMoveScore is 0 then one of the checks is failed
        return tmpMoveScore;
    }


    //checking if the word tiles ar in player pack
    private boolean isContain(Word w) {
        for(Tile t: playerHand){
            if(!(Arrays.stream(w.getTiles()).toList().contains(t))){
                return false;
            }
        }
        return true;
    }


    // func for re-packing the player hand with tiles after placing word on board
    public void initHandAfterMove(Word w) {
        List<Tile>tmpWordList = Arrays.stream(w.getTiles()).toList();
        playerHand = playerHand.stream().filter((t)->!tmpWordList.contains(t)).collect(Collectors.toList());
        while(!handIsFull()){
            playerHand.add(Tile.Bag.getBag().getRand());
            handSize++;
        }
    }
    // first initialization of players pack.

    public void initHand(){
        for(int i = 0; i < handSize; i++){
            playerHand.add(Tile.Bag.getBag().getRand());
        }
    }


    //Getters
    public boolean handIsFull()
    {
        return playerHand.size() == 7;
    }

    public int getHandSize()
    {
        return handSize;
    }

    public List<Tile> getPlayerHand()
    {
        return this.playerHand;
    }

    public String getWordQuery()
    {
        return wordQuery;
    }

    public int getId() {return id;}

    public void setWordQuery(String q)
    {
        this.wordQuery = q;
    }



}