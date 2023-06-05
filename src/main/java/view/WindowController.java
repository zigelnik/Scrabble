package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Model;

import java.util.Observable;

public class WindowController extends Observable {

    @FXML
    Button hostB;
    @FXML
    Button joinB;

    Model m = new Model();




    public void host()
    {
        Thread hostThread = new Thread(() -> {
            m.hostGame(9995);
        });
        hostThread.start();
    }

    public void join()
    {
        Thread joinThread = new Thread(()-> {
            m.joinGame("localhost", 9995);
        });
        joinThread.start();
    }


}