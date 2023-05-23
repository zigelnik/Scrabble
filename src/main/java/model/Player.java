package model;

import model.concrete.Board;
import model.concrete.Tile;
import model.concrete.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    String playerName;
    int id;
    List<Tile> pack;
    int packSize; // physical size of tiles
    int sumScore;
    Word query;


    public Player(){
        this.id = 0;
        this.pack = new ArrayList<>();
        this.packSize = 7;
        this.sumScore = 0;
    }

    public int makeMove(Word w){
        int tmpMoveScore = 0;
        // if tiles are over
        if(packSize == 0){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player wants to place a word with no enough tiles
        else if(w.getTiles().length > packSize){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player don't have all the tiles for word
        else if(!isContainTilesForWord(w)){
            System.out.println("Not all word tiles are existed");
            return tmpMoveScore;
        }
        tmpMoveScore += GameState.getBoard().tryPlaceWord(w); // placing the word at the same board
        // after all checks,decline the words size from pack and init pack back to 7.
        if(tmpMoveScore != 0){
            packSize -= w.getTiles().length;
            initPackAfterMove(w);
        }
        sumScore += tmpMoveScore;
        //if tmpMoveScore is 0 then one of the checks is failed
        return tmpMoveScore;
    }


    //checking if the word tiles ar in player pack
    private boolean isContainTilesForWord(Word w) {
        for(Tile t: pack){
            if(!(Arrays.stream(w.getTiles()).toList().contains(t))){
                return false;
            }
        }
        return true;
    }


    // func for re-packing the player hand with tiles after placing word on board
    public void initPackAfterMove(Word w) {
        List<Tile>tmpWordList = Arrays.stream(w.getTiles()).toList();
        pack = pack.stream().filter((t)->!tmpWordList.contains(t)).collect(Collectors.toList());
        while(!packIsFull()){
            pack.add(Tile.Bag.getBag().getRand());
            packSize++;
        }
    }
    // first initialization of players pack.

    public void initPack(){
        for(int i =0; i < packSize; i++){
            pack.add(Tile.Bag.getBag().getRand());
        }
    }

    public void convertStrToWord(String strQuery){
        //EXAMPLE: "CAR,5,6,False"
        String[] res = strQuery.split(",");
        String word = res[0];
        int row = Integer.parseInt(res[1]);
        int col = Integer.parseInt(res[2]);
        boolean vert = Boolean.parseBoolean(res[3]);

        //after parsing the strings , creating new Word
        Word tmpQuery = new Word(getTileArr(word), row, col, vert);

    }

    // converting string to Tiles[] for creating new Word
    public Tile[] getTileArr(String str) {
        Tile[] tileArr =new Tile[str.length()];
        int i=0;
        for(char ch: str.toCharArray()) {
            tileArr[i]= Tile.Bag.getBag().getTile(ch);
            i++;
        }
        return tileArr;
    }


    //Getters
    public boolean packIsFull()
    {
        return pack.size() == 7;
    }

    public int getPackSize()
    {
        return packSize;
    }

    public List<Tile> getPack()
    {
        return this.pack;
    }

    public Word getQuery()
    {
        return query;
    }

    public int getId() {return id;}

    public void setQuery(Word q)
    {
        this.query = q;
    }



}