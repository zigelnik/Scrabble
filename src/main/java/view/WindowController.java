package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Observable;

public class WindowController extends Observable {

    @FXML
    Button hostB;
@FXML
Button joinB;


    public void host()
    {
       // GameServer.start();
    }

    public void join()
    {
        //GameClient.start();
    }


}