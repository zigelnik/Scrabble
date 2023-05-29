package model.concrete;

import model.concrete.GameState;
import model.concrete.Player;
import model.concrete.Tile;
import model.concrete.Word;
import model.logic.BookScrabbleHandler;
import model.logic.DictionaryManager;
import model.network.QueryServer;
import model.network.GameClientHandler;
import model.network.GameServer;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HostPlayer extends Player {

    private BufferedReader consoleReader;
    public QueryServer queryServer;
    public int port = 9998;

    public HostPlayer(GameState gs) {
       System.out.println("Host, enter your name:");
        try {
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            this.setName(consoleReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gameState = gs;
        gameState.addPlayer(this);
        queryServer = new QueryServer(port,new BookScrabbleHandler());

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
        while(!gameState.getIsGameOver())
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

               // updateGame();
            }
        }
    }

    // optional: updating all clients with the updates game state
        public void updateGame()
        {
            for(GameClientHandler gch: GameServer.getClients())
            {
                gch.updateClientsState(gameState);
            }
        }

    public boolean legalMove(Player player)
    {
        boolean validQuery;
        String msg = null;
        int score=0;

                  // if the player is the host
        if(player.getClass().equals(this.getClass()))
        {
            System.out.println("Host, enter your query: ");
            try {
                msg = consoleReader.readLine();
            } catch (IOException e) {
                System.out.println("bad input");;
            }
        }

        else { // if the player is a regular player
            for (GameClientHandler gch : GameServer.getClients()) {
                if (gch.player.equals(player)) {
                    msg = gch.getMessageQuery();

                }
            }
        }

        String[] query = msg.split(",");
        String tmp = gameState.getTextFiles();
        String dicWord = "Q," + tmp + query[0];
        // TODO: should we get a _ from the query and try to complete the word before sending it to make move?

        validQuery = tmpDictionaryLegal(dicWord);
        if(validQuery)
        {
            score=  makeMove(gameState.convertStrToWord(msg),player);
            player.sumScore += score;
            return score != 0;
        }

        return false; //set to change
    }

    public int makeMove(Word w, Player p){
        // if makeMove fails this integer will stay 0.
        int tmpMoveScore = 0;

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
        else if(!isContain(w,p)){
            System.out.println("Not all word tiles are existed");
            return tmpMoveScore;
        }
        tmpMoveScore += gameState.board.tryPlaceWord(w); // placing the word at the same board
        // after all checks,decline the words size from pack and init pack back to 7.
        if(tmpMoveScore != 0){
            p.setHandSize(p.getHandSize() - w.getTiles().length);
            initHandAfterMove(w , p);
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
            p.getPlayerHand().add(gameState.bag.getRand());
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

