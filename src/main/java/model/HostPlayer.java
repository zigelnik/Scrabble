package model;

import model.concrete.Tile;
import model.concrete.Word;
import model.logic.BookScrabbleHandler;
import model.logic.Dictionary;
import model.logic.DictionaryManager;
import model.logic.QueryServer;

import java.io.*;
import java.net.Socket;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HostPlayer extends  Player{

    private GameState gameState;
    private BufferedReader consoleReader;
    public QueryServer queryServer;
    public int port = 9998;
    static boolean flag = false;

    public HostPlayer(GameState gs) {
        gameState = gs;
        gameState.addPlayer(this);
        queryServer = new QueryServer(port,new BookScrabbleHandler());
        consoleReader = new BufferedReader(new InputStreamReader(System.in));

    }
    public void initGame(){

        int currPlayerInd = 1;
        gameState.setTurns(); // players turns by their index in playerList

        try {
            gameState.initHands();
        }catch(Exception e)
        {
            System.out.println("problem inithands");
            e.printStackTrace();
        }

        //  loadBooks();
        while(!gameState.isGameOver)
        {
            for(Player player : gameState.playersList)
            {

                while(!player.isTurnOver)
                {
                    player.isTurnOver =  legalMove(player);

                }
                player.isTurnOver = false; // returning so next round the player can play again his turn.

                currPlayerInd = ((currPlayerInd+1) % gameState.playersList.size());

                // do we need to get the winner as object or change the isWinner to void?
                Player winner = gameState.isWinner();
            }

        }

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
//        else if(!isContain(w)){
//            System.out.println("Not all word tiles are existed");
//            return tmpMoveScore;
//        }
        tmpMoveScore += gameState.board.tryPlaceWord(w); // placing the word at the same board
        // after all checks,decline the words size from pack and init pack back to 7.
        if(tmpMoveScore != 0){
            handSize -= w.getTiles().length;
            initHandAfterMove(w);
        }
        sumScore += tmpMoveScore;
        //if tmpMoveScore is 0 then one of the checks is failed
        return tmpMoveScore;
    }


    public boolean legalMove(Player player)
    {
        boolean validQuery;
        String msg = null;
        int score=0;
        if(player.getClass().equals(this.getClass()))
        {
            System.out.println("Host, enter your query: ");
            try {
                msg = consoleReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            for (GameClientHandler gch : GameServer.getClients()) {
                if (gch.player.equals(player)) {
                    msg = gch.getMessageQuery();

                }
            }
        }


        String[] query = msg.split(",");


        String tmp = gameState.getTextFiles();
        String dicWord = "Q," + tmp + query[0];

        validQuery = tmpDictionaryLegal(dicWord);
        if(validQuery)
        {
            score=  makeMove(convertStrToWord(msg));
            player.sumScore += score;
            return score != 0;
        }

        return false; //set to change
    }


    // there is dictionaryLegal method from patam1 , return always True.
    public boolean tmpDictionaryLegal(String query ){
        //TODO: with given word we will open new thread to dictionaryServer
        //TODO: check with dm if the word is legal , return true or false
        //TODO: closing the tread, this method will run each time a player want to make move
        System.out.println("before dictionaryLegal");

        boolean rightWord = false;
        queryServer.start();

        try {
            DictionaryManager dm = DictionaryManager.get();

            Socket server = new Socket("localhost", port);
            PrintWriter out = new PrintWriter(server.getOutputStream());

            Scanner in = new Scanner(server.getInputStream());

            out.println(query);
            out.flush();
            String res = in.next();
            System.out.println(res);
            if ( res.equals("true")) {
                rightWord = true;
            }
            else
            {
                System.out.println("problem getting the right answer from the server (-10)");
            }
            in.close();
            out.close();
            server.close();
        } catch (IOException e) {
            System.out.println("your code ran into an IOException (-10)");
            e.printStackTrace();

        }
        queryServer.close();

        return rightWord;
    }


    public Word convertStrToWord(String strQuery){
        //EXAMPLE: "CAR,5,6,False"
        String[] res = strQuery.split(",");
        String word = res[0];
        int row = Integer.parseInt(res[1]);
        int col = Integer.parseInt(res[2]);
        boolean vert = Boolean.parseBoolean(res[3]);

        //after parsing the strings , creating new Word
        Tile[] wordTile = getTileArr(word.toUpperCase());
        Word tmpQuery = new Word(wordTile, row, col, vert);
        System.out.println("after convert str to word");
            return tmpQuery;
    }

    // converting string to Tiles[] for creating new Word
    public  Tile[] getTileArr(String str) {
        Tile[] tileArr =new Tile[str.length()];
        int i=0;
        for(char ch: str.toCharArray()) {
            tileArr[i]= gameState.bag.getTile(ch);
            i++;
        }
        return tileArr;
    }




    // func for re-packing the player hand with tiles after placing word on board
    public void initHandAfterMove(Word w) {
        List<Tile>tmpWordList = Arrays.stream(w.getTiles()).toList();
        playerHand = playerHand.stream().filter((t)->!tmpWordList.contains(t)).collect(Collectors.toList());
        while(!handIsFull()){
            playerHand.add(gameState.bag.getRand());
            handSize++;
        }
    }


    private boolean isContain(Word w) {
        for(Tile t: playerHand){
            if(!(Arrays.stream(w.getTiles()).toList().contains(t)) && t != null){
                return false;
            }
        }
        return true;
    }
}

