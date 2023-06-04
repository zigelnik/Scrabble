package view_model;

import javafx.beans.property.*;
import model.Model;

import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    static Model m = Model.getModel();
    IntegerProperty playerScore;
    StringProperty playerName;


    public ViewModel() {
        playerScore = new SimpleIntegerProperty();

    }
    public static void hostGame(int port,String name)
    {
        m.hostGame(port,name);
    }
    public static void joinGame(String ip, int port, String name) {
        m.joinGame(ip,port,name);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Model getModel() {return m;}
    public IntegerProperty getPlayerScore() {return playerScore;}


}
