package model;

public interface Facade {


    void hostGame(int port,String name);

    void joinGame(String ip, int port,String name);

    void disconnect();


}
