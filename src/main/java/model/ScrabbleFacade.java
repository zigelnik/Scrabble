package model;

import model.logic.MyServer;

public interface ScrabbleFacade {


    void hostGame(int port);

    void joinGame(String ip, int port );


    void addPlayer(String name);
    void disconnect();


}
