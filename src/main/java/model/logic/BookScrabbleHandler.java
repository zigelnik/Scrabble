package model.logic;

import java.io.*;
import java.util.Scanner;


public class BookScrabbleHandler implements  ClientHandler{
    //BufferedReader in;
    Scanner in;
    PrintWriter out;
    DictionaryManager dm;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        try{

            dm = new DictionaryManager();
            // in = new BufferedReader(new InputStreamReader(inFromclient));
            in = new Scanner(inFromclient);
            out = new PrintWriter(outToClient,true);

            String line;
            String[] args;
            String byMethod;

            line = in.nextLine();
//            System.out.println(line);
            byMethod = String.valueOf(line.charAt(0)) + line.charAt(1);
//            System.out.println(byMethod);

            args = line.substring(2).split(",",0);

            if (byMethod.equals("C,"))
                out.println(dm.challenge(args));

            else if(byMethod.equals("Q,"))
                out.println(dm.query(args));

            out.flush();
            outToClient.flush();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void close()  {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
