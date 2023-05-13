package model;

import model.logic.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PlayerHandler {

    private Socket socket;
    BufferedReader in;
    PrintWriter out;
    public PlayerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run()
    {
        try{
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             out = new PrintWriter(socket.getOutputStream(), true);
            Scanner cmd = new Scanner(System.in);

            out.println("hello");
            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("player handler problem");
        }
    }


}
