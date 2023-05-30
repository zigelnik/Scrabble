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

    @FXML
    Button hostB;
    @FXML
    Button joinB;
    @FXML
     TextField nameLabel;

    ViewModel vm;



    public void setViewModel(ViewModel gameViewModel) {
        this.vm = gameViewModel;


    }

    @FXML
    public void host()
    {   hostB.setVisible(false);
        joinB.setVisible(false);
        nameLabel.setVisible(true);
        System.out.println(nameLabel.getText());
        vm.getModel().getGch().getHostPlayer().setName(nameLabel.getText());
        vm.getModel().hostGame(9996);
    }

    public void join()
    {

        hostB.setVisible(false);
        joinB.setVisible(false);
        nameLabel.setVisible(true);
        vm.getModel().getGch().setPlayerName(new Player(),nameLabel.getText());
        vm.getModel().joinGame("localhost", 9996);

    }

    @Override
    public void update(Observable o, Object arg) {

    }


}