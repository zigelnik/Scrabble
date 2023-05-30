package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Model;
import view_model.ViewModel;

import java.util.Observable;
import java.util.Observer;

public class WindowController implements Observer {

    @FXML
    Button hostB;
    @FXML
    Button joinB;
    ViewModel vm;


    
    public void host()
    {
        vm.getModel().hostGame(9996);

    }

    public void join()
    {
        vm.getModel().joinGame("localhost", 9996);
    }


    public void setViewModel(ViewModel gameViewModel) {
        this.vm = gameViewModel;
    }

    @Override
    public void update(Observable o, Object arg) {

    }


}