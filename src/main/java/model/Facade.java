package model;

public interface Facade {


    void hostGame(int port);

    void joinGame(String ip, int port );



    void disconnect();


}
