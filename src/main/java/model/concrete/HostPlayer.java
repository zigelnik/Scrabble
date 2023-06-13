package model.concrete;


import javafx.application.Platform;
import model.Model;
import model.logic.DictionaryManager;
import model.network.BookScrabbleHandler;
import model.network.QueryServer;
import model.network.GameClientHandler;
import model.network.GameServer;
import view.GamePage;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HostPlayer extends Player {

    private BufferedReader consoleReader;
    public volatile boolean stop;
    public QueryServer queryServer;
    public int port = 9998;
    Model m = Model.getModel();


    public HostPlayer(GameState gs,String name) {
        gameState = gs;
        GameState.getGM().addPlayer(this);
        queryServer = new QueryServer(port,new BookScrabbleHandler());
        this.setName(name);
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));

    }

    public void initPlayersHand(){
        try {
            GameState.getGM().initHands();

        }catch(Exception e)
        {
            System.out.println("problem init-hands");
            e.printStackTrace();
        }

    }

    public void initGame(){

        int currPlayerInd = 1;
        gameState.setTurns(); // players turns by their index in playerList
        //  loadBooks();
        while(!GameState.getGM().getIsGameOver())
        {
            for(Player player : GameState.getGM().playersList)
            {
                while(!player.isTurnOver)
                {
                    player.isTurnOver =  legalMove(player);
                }
                m.updatePlayerVals(player.getSumScore(),player.convertTilesToStrings(playerHand)); // updating PlayerHand and Score
                player.isTurnOver = false; // returning so next round the player can play again his turn.
                currPlayerInd = ((currPlayerInd+1) % gameState.playersList.size());

                // do we need to get the winner as object or change the isWinner to void?
                Player winner = gameState.isWinner();

                GameServer.broadcastToClients(player.acceptedQuery);

            }
            //TODO: fix the main bug when using multiple clients!
            //TODO:The break and the stop=true comment is preventing infinty loop when testing one player!
            break;
        }
        stop=true;
    }


    public boolean legalMove(Player player)
    {
        String msg = null;
        int score=0;

        // if the player is the host
        if (player.getClass().equals(this.getClass())) {
            System.out.println("Host, enter your query and press Submit: ");
//                        try {
//                            msg = consoleReader.readLine();
//                        } catch (IOException e) {
//                            System.out.println("bad input");
//                            ;
//                        }
        } else { // if the player is a regular player
            for (GameClientHandler gch : GameServer.getClients()) {
                if (gch.player.equals(player)) {
//                    msg = gch.getMessageQuery();
                    System.out.println("Enter your query and press Submit: ");

                }
            }
        }
        synchronized (GamePage.getGP().getLockObject()) {
            try {
                GamePage.getGP().getLockObject().wait(); // Releases the lock and waits until notified
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            msg = Model.getModel().getPlayerQuery();
            System.out.println("msg from legalMove is: " + msg);
        }

        if (msg != null) {score=  makeMove(msg,player);}
        else{System.out.println("Walla msg is NULL!");}
        player.sumScore += score;
        return score != 0;
    }

    public int makeMove(String msg , Player p){
        // if makeMove fails this integer will stay 0.
        int tmpMoveScore = 0;
        String[] args = msg.split(","); // splitting the query by 4 commas <word,ROW,COL,alignment>
        String books = gameState.getTextFiles();
        String queryWord = "Q,"+books+args[0];
        Word w = gameState.convertStrToWord(msg);
        boolean validQuery;

        // if tiles are over
        if(p.getHandSize() == 0){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player wants to place a word with not enough tiles
        else if(w.getTiles().length > p.getHandSize()){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player don't have all the tiles for the word
//        else if(!isContain(w,p)){
//            System.out.println("Not all word tiles are existed");
//            return tmpMoveScore;
//        }

        // after checking all exceptions, check if the word exist in the books
        validQuery = tmpDictionaryLegal(queryWord);

        if(validQuery) // if validQuery returned true -> the word exists, now we try placing the word on the board
            tmpMoveScore += gameState.getBoard().tryPlaceWord(w);

        // after all checks,decline the words size from pack and init pack back to 7.
        if(tmpMoveScore != 0){
            p.setHandSize(p.getHandSize() - w.getTiles().length);
            initHandAfterMove(w , p);
            p.acceptedQuery = msg;
        }
        p.setSumScore(p.getSumScore()+ tmpMoveScore);
        //if tmpMoveScore is 0 then one of the checks is failed
        return tmpMoveScore;
    }

    // there is dictionaryLegal method from patam1 , return always True.
    public boolean tmpDictionaryLegal(String query ){
        //TODO: with given word we will open new thread to dictionaryServer
        //TODO: check with dm if the word is legal , return true or false
        //TODO: closing the tread, this method will run each time a player want to make move

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


    // func for re-packing the player hand with tiles after placing word on board
    public void initHandAfterMove(Word w, Player p) {
        List<Tile>tmpWordList = Arrays.stream(w.getTiles()).toList();
        p.setPlayerHand(p.getPlayerHand().stream().filter((t)->!tmpWordList.contains(t)).collect(Collectors.toList()));
        while(!handIsFull()){
            p.getPlayerHand().add(gameState.getBag().getRand());
            p.setHandSize(p.getHandSize()+1);
        }
    }

    public boolean isContain(Word w, Player p) {
        for(Tile t: p.getPlayerHand()){
            if(!(Arrays.stream(w.getTiles()).toList().contains(t)) && t != null){
                return false;
            }
        }
        return true;
    }
}

