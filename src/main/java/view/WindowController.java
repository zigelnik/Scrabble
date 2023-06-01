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
    LandingPage entryPage;
    WaitingPage waitingPage;
    GamePage gamePage;


    public WindowController(ViewModel vm, LandingPage entryPage, WaitingPage waitingPage, GamePage gamePage) {
        this.vm = vm;
        this.entryPage = entryPage;
        this.waitingPage = waitingPage;
        this.gamePage = gamePage;
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