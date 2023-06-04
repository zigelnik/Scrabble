package model;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;

import model.concrete.Tile;
import model.network.Client;
import model.network.GameClientHandler;
import model.network.GameServer;
import model.network.QueryServer;


public class Model extends Observable implements Facade {

    GameServer hostServer;


    @Override
        public void hostGame(int port,String name) {
            hostServer = new GameServer(port,name);
            hostServer.start();
        }

        @Override
        public void joinGame(String ip, int port,String name) {
            Client client = new Client(ip, port,name);
            client.start();
        }

        @Override
        public void disconnect() {

        }

    public GameServer getHostServer() {return hostServer;}


    private  static class ModelHolder{ public static final Model m = new Model();}
    public static Model getModel() {return ModelHolder.m;}

}
