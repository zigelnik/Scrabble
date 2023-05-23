package model;
import java.io.*;
import java.net.*;
import java.util.*;

//TODO: why we need GameClient? why just not consider GuestPlayer as GameClinet and just open the sovket overthere?

// Client class
public class GuestPlayer extends Player {
    String ip;
    int port;
    Socket socket;

    BufferedReader reader;
    BufferedReader serverReader;
    PrintWriter writer;

    public GuestPlayer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    // driver code
    public  void start() {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to server.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Enter your name: ");
            String clientName = reader.readLine();
            writer.println(clientName);

            // receiving msg
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = serverReader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // sending msg
            String message;
            while ((message = reader.readLine()) != null) {
                writer.println(message);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // sending queries to host
    public void sendQuery(String message)
    {

        try {
            if ((message = reader.readLine()) != null) {
                writer.println(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
