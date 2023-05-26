package model;
import java.io.*;
import java.net.*;

//TODO: why we need GameClient? why just not consider GuestPlayer as GameClinet and just open the sovket overthere?

// Client class
public class Client {
    String ip;
    int port;
    Socket socket;

    BufferedReader consoleReader;
    BufferedReader readFromServer;
    PrintWriter writeToServer;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    // driver code
    public  void start() {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to server.");

             consoleReader = new BufferedReader(new InputStreamReader(System.in));
             readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             writeToServer = new PrintWriter(socket.getOutputStream(), true);


            // receiving msg
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = readFromServer.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // sending msg
            String message;
            while ((message = consoleReader.readLine()) != null) {
                writeToServer.println(message);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // sending queries to host
//    public void sendQuery()
//    {
//            String message=null;
//        try {
//            if ((message = consoleReader.readLine()) != null) {
//                writer.println(message);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //  this.setWordQuery(message);
//        System.out.println("after senqauery");
//    }
}
