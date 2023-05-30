package model;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;

import model.network.Client;
import model.network.GameClientHandler;
import model.network.MyServer;


    public class Model extends Observable implements Facade {

        MyServer hostServer;

        @Override
        public void hostGame(int port) {
            hostServer = new MyServer(port, new GameClientHandler());
            hostServer.start();
        }

        @Override
        public void joinGame(String ip, int port) {
            Client client = new Client(ip, port);
            client.start();
        }

        @Override
        public void disconnect() {
            
        }
    }
