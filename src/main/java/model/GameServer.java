package model;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


import java.io.*;
import java.net.*;

// Server class
public class GameServer {
    int port;

    public GameServer(int port) {
        this.port = port;
    }

    public  void start()
    {
        int numOfPlayers=0;
        ServerSocket server = null;

        try {
            System.out.println("initiating game server");
            server = new ServerSocket(port);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {
                Socket client = server.accept();

                if(numOfPlayers < 3) {
                    numOfPlayers++;
                    String clientName = "Client #"+numOfPlayers;
                    // socket object to receive incoming client
                    // requests

                    // Displaying that new client is connected
                    // to server
                    System.out.println("\nNew client connected from: "
                            + client.getInetAddress()
                            .getHostAddress()+" number of clients: "+numOfPlayers);

                    // create a new thread object
                    ClientHandler clientSock
                            = new ClientHandler(client, clientName);

                    // This thread will handle the client
                    // separately
                    new Thread(clientSock).start();
                }
                else {
                    System.out.println("too much clients!");
                    client.close();
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ClientHandler class
    private  class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private String clientName;

        // Constructor
        public ClientHandler(Socket socket, String name)
        {
            this.clientSocket = socket;
            this.clientName = name;
        }

        @Override
        public void run()
        {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // get the outputstream of client
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                // get the inputstream of client
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    // writing the received message from
                    // client
                    System.out.printf(
                            " Sent from: "+clientName+" "+line+"\n");
                    out.println(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
