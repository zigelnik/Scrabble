package view_model;

import javafx.beans.property.*;
import model.Model;

import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    static Model m;
    IntegerProperty playerScore;
    StringProperty playerName;


    public ViewModel(Model m) {
        this.m = m;
        playerScore = new SimpleIntegerProperty();

    }
    public static void hostGame(int port)
    {
        m.hostGame(port);
    }
    public static void joinGame(String ip, int port) {
        m.joinGame(ip,port);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Model getModel() {return m;}
    public IntegerProperty getPlayerScore() {return playerScore;}
}
