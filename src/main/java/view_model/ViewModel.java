package view_model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.Model;

import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    Model m;




    public ViewModel(Model m) {
        this.m = m;

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Model getModel() {return m;}
}
