package model;

import model.logic.BookScrabbleHandler;
import model.logic.DictionaryManager;
import model.logic.MyServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HostPlayer extends  Player{

    public  MyServer queryServer;
    public int port = 9997;

    public HostPlayer() {
        queryServer = new MyServer(port,new BookScrabbleHandler());
    }

    // there is dictionaryLegal method from patam1 , return always True.
    public boolean tmpDictionaryLegal(String query ){
        //TODO: with given word we will open new thread to dictionaryServer
        //TODO: check with dm if the word is legal , return true or false
        //TODO: closing the tread, this method will run each time a player want to make move
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
            if (( !res.equals("true")))
                  System.out.println("problem getting the right answer from the server (-10)");
            in.close();
            out.close();
            server.close();
        } catch (IOException e) {
            System.out.println("your code ran into an IOException (-10)");
            e.printStackTrace();

        }
        queryServer.close();

        return true;
    }

        public void loadBooks(String... args)
        {

        }
}
