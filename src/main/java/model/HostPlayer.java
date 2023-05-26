package model;

import model.concrete.Tile;
import model.concrete.Word;
import model.logic.BookScrabbleHandler;
import model.logic.Dictionary;
import model.logic.DictionaryManager;
import model.logic.QueryServer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HostPlayer extends  Player{

    private  GameState gameState;
    public QueryServer queryServer;
    public int port = 9998;
    static boolean flag = false;

    public HostPlayer(GameState gs) {
        gameState = gs;
        gameState.addPlayer(this);
        queryServer = new QueryServer(port,new BookScrabbleHandler());
    }
    public void initHands(){
        for(int i = 0; i < gameState.playersList.size(); i++){
            for(int j=0;j<gameState.playersList.get(i).handSize;j++)
            gameState.playersList.get(i).playerHand.add(gameState.bag.getRand());
        }
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
            if(!(Arrays.stream(w.getTiles()).toList().contains(t))){
                return false;
            }
        }
        return true;
    }
    public int makeMove(Word w, GameState gameState){
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
    public void initGame(){
        System.out.println("init");

        int currPlayerInd = 1;
      //  gameState.setTurns(); // players turns by their index in playerList
        initHands();
        System.out.println("after playerlist");


      //  loadBooks();
        while(!gameState.isGameOver)
        {
            System.out.println("after game is over");
            for(GameClientHandler gch: GameServer.getClients())
            {

                System.out.println("after for player list");

                while(!gch.player.isTurnOver)
               {
                    System.out.println("before legal move");
                    gch.player.isTurnOver =  legalMove(gch);

                }
                gch.player.isTurnOver = false; // returning so next round the player can play again his turn.

                currPlayerInd = ((currPlayerInd+1) % gameState.playersList.size());

                // do we need to get the winner as object or change the isWinner to void?
                Player winner = gameState.isWinner();
            }

        }


        // System.out.println(hp.tmpDictionaryLegal("Q,mobydick.txt,"+"TOKEN"));
    }


    public boolean legalMove(GameClientHandler gch)
    {
        int score=0;

        String msg = gch.getMessageQuery();

                    // CAR,4,5,true
          // msg = gch.player.getWordQuery();

        String[] query = msg.split(",");

       // msg = "Q,mobydick.txt,"+"TOKEN";
        String dicWord = "Q,mobydick.txt,"+query[0];
        boolean validQuery;
        validQuery = tmpDictionaryLegal(dicWord);
        Word word = convertStrToWord(msg);
        if(validQuery)
        {
            System.out.println("before make move");
            score=  makeMove(word,gameState);

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
        // [*] get input(tiles) from client(button's gui?) make it to a string so we can
        // send it as a query to the BookScrabbleHandler
        // [*] put tests here like eli did in mainTrain -> testBSCH
        try {
            DictionaryManager dm = DictionaryManager.get();
          //  dm.query("mobydick.txt,LONDON");
            System.out.println("before socket");
            Socket server = new Socket("localhost", port);
            PrintWriter out = new PrintWriter(server.getOutputStream());
            System.out.println("before scanner");

            Scanner in = new Scanner(server.getInputStream());
            System.out.println("before outprintln(query)");

            out.println(query);
            out.flush();
            System.out.println("after flush, before in,next");
            String res = in.next();
            System.out.println("after in next");
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
        System.out.println("after dictionaryLegal");

        return rightWord;
    }

        public void loadBooks()
        {

            System.out.println("in laod books");
           Dictionary d = new Dictionary("mobydick.txt");


        }


    public Word convertStrToWord(String strQuery){
        //EXAMPLE: "CAR,5,6,False"
        String[] res = strQuery.split(",");
        String word = res[0];
        int row = Integer.parseInt(res[1]);
        int col = Integer.parseInt(res[2]);
        boolean vert = Boolean.parseBoolean(res[3]);

        //after parsing the strings , creating new Word
        Tile[] wordTile = getTileArr(word);
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
}

/*
	private static Tile[] get(String s) {
		Tile[] ts=new Tile[s.length()];
		int i=0;
		for(char c: s.toCharArray()) {
			ts[i]=Bag.getBag().getTile(c);
			i++;
		}
		return ts;
	}
 */