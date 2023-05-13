package model;

import model.logic.ClientHandler;
import model.logic.MainTrain;
import model.logic.MyServer;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void client1(int port) throws Exception {
        Socket server = new Socket("localhost", port);

        PlayerHandler player = new PlayerHandler(server);
        player.run();
//        PrintWriter outToServer = new PrintWriter(server.getOutputStream());
//        Scanner in = new Scanner(server.getInputStream());
//        outToServer.println("im a client");
//        outToServer.flush();
//        String response = in.next();
//        if (response == null)
//            System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
//        in.close();
//        outToServer.println("byebye");
//        outToServer.close();
        server.close();
    }
    public static class ClientHandler1 implements ClientHandler {
        PrintWriter out;
        Scanner in;

        @Override
        public void handleClient(InputStream inFromclient, OutputStream outToClient) {
            out = new PrintWriter(outToClient);
            in = new Scanner(inFromclient);
            String line = in.nextLine();
                    if(line.equals("hello server"))
                    {
                        System.out.println("hi client");
                    }
                    else{
                        System.out.println("mi ata ?");

                    }
            out.flush();
        }

        @Override
        public void close() {
            in.close();
            out.close();
        }

    }
    public static void main(String[] args) {

//        ScrabbleFacade game = new ScrabbleFacade();
//        game.hostGame("5032");

          int port = 5032;
        MyServer server = new MyServer(port, new ClientHandler1());
        boolean ok = true;
        int c = Thread.activeCount();
        server.start(); // runs in the background
        try {
            client1(port);
        } catch (Exception e) {
            System.out.println("some exception was thrown while testing your server, cannot continue the test (-100)");
            ok = false;
        }
        server.close();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        if (Thread.activeCount() != c) {
            System.out.println("you have a thread open after calling close method (-100)");
            ok = false;
        }
    }
}
