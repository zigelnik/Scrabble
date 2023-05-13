package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.GameClient;
import model.GameServer;
import view_model.ViewModel;

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