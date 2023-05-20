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
    public static GameState gameInstance = GameState.getGameState();

    public GameClientHandler() {
    }

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
          handlerList.add(this);

        try {
//     TODO:       ObjectOutput op =  new ObjectOutputStream(outToClient);
//            op.writeObject(gameInstance);

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
                out.flush();

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
