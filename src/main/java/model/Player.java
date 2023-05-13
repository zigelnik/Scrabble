package model;

import model.concrete.Board;
import model.concrete.Tile;
import model.concrete.Word;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static int playersCounter = 1; // NOTE: with always the playersCounter is +1 from real playersNum
    String playerName;
    int id;
    Tile[] pack;
    int packSize; // physical size of tiles
    int sumScore;

    Player(){
        // players id is from 1-4
        this.id = playersCounter++;
        this.pack = new Tile[7];
        this.packSize = 7;
        this.sumScore = 0;
    }

    void makeMove(Word w){
        //corner cases: what if return 0? something went wrong with his word?
        // what if the tiles are over?
        // how the dictionary is presented? with file.txt?
        sumScore += Board.getBoard().tryPlaceWord(w);
    }


    //Functions for managing players racks
    public void initPack(){
        for(int i =0; i < pack.length; i++){
            pack[i] = Tile.Bag.getBag().getRand();
        }
    }

    public Tile getAndRemoveFromPack(int tileInd)
    {
        Tile temp = pack[tileInd];
        pack[tileInd] = null;
        return temp;
    }


    //adds tile to next free index in rack array then , returns -1 if not found.
    public int addTileToPack(Tile tile)
    {
        for(int i = 0; i < pack.length; i++)
        {
            if(pack[i] == null)
            {
                pack[i] = Tile.Bag.getBag().getRand();
                packSize++;
                return i;
            }
        }
        return -1;
    }

    //Getters
    public boolean packIsFull()
    {
        return packSize == 7;
    }

    public int getPackSize()
    {
        return packSize;
    }

    public Tile[] getPack()
    {
        return this.pack;
    }



}
