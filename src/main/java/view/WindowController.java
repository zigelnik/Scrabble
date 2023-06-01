package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Model;
import model.concrete.Player;
import view_model.ViewModel;

import java.util.Observable;
import java.util.Observer;

public class WindowController implements Observer {


    ViewModel vm;


    public WindowController(ViewModel vm) {
        this.vm = vm;
    }

    public void host()
    {
        Thread hostThread = new Thread(()-> {
            vm.getModel().hostGame( 9996);
        });

    }

    public void join()
    {
        Thread joinThread = new Thread(()-> {
            vm.getModel().joinGame("localhost", 9996);
        });

    }

    @Override
    public void update(Observable o, Object arg) {

    }




}