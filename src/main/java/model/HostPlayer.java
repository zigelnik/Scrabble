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
import java.util.List;
import java.util.Scanner;

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

    public void initGame(){
        System.out.println("init");

        int currPlayerInd = 1;
       gameState.setTurns(); // players turns by their index in playerList
        gameState.playersList.stream().forEach((p)->p.initHand());

        System.out.println("after playerlist");


        loadBooks();
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
        /*
        *
        *             //this is the player that his turn now
            if (getHost() != null)
            {
                //TODO: make the clients always trying to attend to the host, only when its clear the host
                //will coneect him else he will get message : its not you turn
                //TODO: put all of this in loop, what if he mistakes? it his turn again.
                ((HostPlayer) getHost()).tmpDictionaryLegal(tmpPlayer.getWordQuery().toString());
                tmpPlayer.makeMove(tmpPlayer.getWordQuery());
            }
            * */
        String msg=null;
        if(gch.player.getClass().equals(Player.class))
        {
//            System.out.println("before sendquery");
                 //gch.sendQuery();
//            /* client interacting with bookscrabble handler */
           msg = gch.player.getWordQuery();

        }
       // msg = "Q,mobydick.txt,"+"TOKEN";
        boolean validQuery = true;
        //validQuery = tmpDictionaryLegal("C");
        if(validQuery)
        {
            System.out.println("before make move");
            score=  gch.player.makeMove(convertStrToWord(msg),gameState);

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
            dm.query("TOKEN");

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
          //  Dictionary d = new Dictionary("mobydick.txt");
        }


    public Word convertStrToWord(String strQuery){
        //EXAMPLE: "CAR,5,6,False"
        String[] res = strQuery.split(",");
        String word = res[0];
        int row = Integer.parseInt(res[1]);
        int col = Integer.parseInt(res[2]);
        boolean vert = Boolean.parseBoolean(res[3]);

        //after parsing the strings , creating new Word
        Word tmpQuery = new Word(getTileArr(word), row, col, vert);
        System.out.println("after convert str to word");
            return tmpQuery;
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
}
