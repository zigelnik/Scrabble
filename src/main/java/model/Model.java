package model;

import model.logic.BookScrabbleHandler;
import model.logic.QueryServer;

import java.util.Observable;

public class Model extends Observable {

    QueryServer queryServer;
    GameState gameState;

    public Model() {
        this.queryServer = new QueryServer(9995,new BookScrabbleHandler());
        this.gameState = new GameState();
    }


    // there is dictionaryLegal method from patam1 , return always True.
    boolean tmpDictionaryLeagal(){
        //TODO: with given word we will open new thread to dictionaryServer
        //TODO: check with dm if the word is legal , return true or false
        //TODO: closing the tread, this method will run each time a player want to make move
        return true;
    }


}
