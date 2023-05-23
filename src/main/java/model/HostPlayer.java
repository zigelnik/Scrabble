package model;

import model.concrete.Tile;
import model.concrete.Word;
import model.logic.BookScrabbleHandler;
import model.logic.DictionaryManager;
import model.logic.QueryServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HostPlayer extends  Player{

    public QueryServer queryServer;
    public int port = 9997;

    public HostPlayer() {
        queryServer = new QueryServer(port,new BookScrabbleHandler());
    }

    // there is dictionaryLegal method from patam1 , return always True.
    public boolean tmpDictionaryLegal(String query ){
        //TODO: with given word we will open new thread to dictionaryServer
        //TODO: check with dm if the word is legal , return true or false
        //TODO: closing the tread, this method will run each time a player want to make move
        boolean rightWord = false;
        queryServer.start();
        // [*] get input(tiles) from client(button's gui?) make it to a string so we can
        // send it as a query to the BookScrabbleHandler
        // [*] put tests here like eli did in mainTrain -> testBSCH

        try {
            DictionaryManager dm = DictionaryManager.get();
            dm.query("TOKEN");
            
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

        public void loadBooks(String... args)
        {

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
