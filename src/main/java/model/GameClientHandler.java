package model;

import model.logic.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameClientHandler implements ClientHandler{


    public static List<GameClientHandler> handlerList= new ArrayList();
    private String clientName;
    PrintWriter out ;
    BufferedReader in ;

    public GameClientHandler() {
            handlerList.add(this);
    }

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        try {

            out = new PrintWriter(
                    outToClient, true);

            in = new BufferedReader(
                    new InputStreamReader(
                            inFromclient));


            String line;
            while ((line = in.readLine()) != null) {

                // writing the received message from
                // client
                System.out.printf(
                        " Sent from client: " /*+ clientName */+ " " + line + "\n");
                out.println(line);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            in.close();
            out.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
