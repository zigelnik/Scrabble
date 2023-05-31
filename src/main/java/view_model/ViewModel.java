package view_model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.Model;

import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    Model m;
    IntegerProperty playerScore;



    public ViewModel(Model m) {
        this.m = m;
        playerScore = new SimpleIntegerProperty();



    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Model getModel() {return m;}
    public IntegerProperty getPlayerScore() {return playerScore;}
}
