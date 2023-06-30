package model.network;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 The QueryServer class represents a server that handles client queries in a network game.
 It listens for incoming connections on a specified port and uses a ClientHandler to handle client requests.
 */

public class QueryServer {

    private int port;
    private ClientHandler ch;
    private volatile boolean stop;
    private ServerSocket server;

    private static int numOfPlayers= 0;

    /**
     * Constructs a new QueryServer object with the specified port and client handler.
     *
     * @param port The port number on which the server listens for incoming connections.
     * @param ch   The client handler used to handle client requests.
     */

    public QueryServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;

    }

    /**
     * Starts the query server and begins accepting incoming connections.
     */
    public void start()

    {
        stop = false;
        new Thread(()-> {
            try {
                startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
    /**

     Starts the server and begins accepting incoming connections.
     @throws Exception If an error occurs while starting the server.
     */
    private void startServer() throws Exception
    {
        server = new ServerSocket(port);
        server.setSoTimeout(1000);
        while(!stop) {

            try {
                Socket aClient = server.accept();

                try {

                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                    ch.close();

                    aClient.close();


                } catch (IOException e) {
                    e.getMessage();
                    ;}
            } catch (SocketTimeoutException e) {
                e.getMessage();

            }
        }
        server.close();
    }

    /**
     * Closes the query server, stopping it from accepting new connections.
     */
    public void close()
    {
        stop = true;
    }

}
