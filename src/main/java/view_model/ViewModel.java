package view_model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.Model;

import java.util.Observable;
import java.util.Observer;

public class ViewModel implements Observer {
    Model m;
    public DoubleProperty aileron,elevators,rudder,throttle;

    public ViewModel(Model m) {
        this.m = m;
        m.addObserver(this);
        aileron= new SimpleDoubleProperty();
        elevators= new SimpleDoubleProperty();
        rudder =  new SimpleDoubleProperty();
        throttle= new SimpleDoubleProperty();

        aileron.addListener((o,ov,nv)->m.setAileron((double)nv));
        elevators.addListener((o,ov,nv)->m.setElevators((double)nv));
        rudder.addListener((o,ov,nv)->m.setRudder((double)nv));
        throttle.addListener((o,ov,nv)->m.setThrottle((double)nv));
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
