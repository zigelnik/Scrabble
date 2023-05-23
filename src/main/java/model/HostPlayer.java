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

    public QueryServer queryServer;
    public int port = 9998;

    public HostPlayer() {
        queryServer = new QueryServer(port,new BookScrabbleHandler());
    }

    public void initGame(){
        System.out.println("init");

        int currPlayerInd = 1;
        List<Player> playerList = GameState.setTurns(); // players turns by their index in playerList
        playerList.stream().forEach((p)->p.initHand());

        System.out.println("after playerlist");


        loadBooks();
        while(!GameState.isGameOver)
        {
            System.out.println("after game is over");
            for(Player player: GameState.playersList)
            {
                System.out.println("after for player list");

                while(!player.isTurnOver)
                {
                    System.out.println("before legal move");
                    player.isTurnOver =  legalMove(player);

                }
                player.isTurnOver = false; // returning so next round the player can play again his turn.

                currPlayerInd = ((currPlayerInd+1) % playerList.size());

                // do we need to get the winner as object or change the isWinner to void?
                Player winner = GameState.isWinner();
            }

        }


        // System.out.println(hp.tmpDictionaryLegal("Q,mobydick.txt,"+"TOKEN"));
    }


    public boolean legalMove(Player player)
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
        if(player.getClass().equals(GuestPlayer.class))
        {
//            System.out.println("before sendquery");
           ((GuestPlayer) player).sendQuery();
//            /* client interacting with bookscrabble handler */
           msg = GameClientHandler.getMessageQuery();

        }
        msg = "Q,mobydick.txt,"+"TOKEN";
        boolean validQuery;
        validQuery = tmpDictionaryLegal(msg);
        if(validQuery)
        {
            System.out.println("before make move");
          //  score=  player.makeMove(convertStrToWord(msg));

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
