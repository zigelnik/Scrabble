package view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import view_model.ViewModel;

import java.util.*;

public class View implements Observer {
    LandingPage landingPage = new LandingPage();
    ViewModel vm = ViewModel.getViewModel();

    public void setViewModel() {
        vm.playerQuery.bind(GamePage.getGP().playerTmpQuery.textProperty());// Binding the ViewModel to View
        GamePage.getGP().scoreLabel.textProperty().bind(vm.score.asString()); // Binding the View to ViewModel
        //binding for the playerHand


    }

    synchronized public void setPlayerHand(){
        // Assuming you have a list of playerHands for each player
        List<List<String>> playerHands = vm.getPlayerHands(); // Get the playerHands for all players
        // Iterate over each player's playerHand and create the rackLabels
        for (List<String> playerHand : playerHands) {
            List<Label> rackLabels = new ArrayList<>();
            int ind = 0;
            for (String strTile : playerHand) {
                Label label = new Label(strTile);
                String tmpStr = playerHand.get(ind);
                StringProperty strProperty = new SimpleStringProperty(tmpStr); // Converting String to StringProperty
                label.textProperty().bindBidirectional(strProperty); // Binding between label and StringProperty
                rackLabels.add(label);
                ind++;
            }
            // Execute the createRack() method on the JavaFX application thread for each player
            Platform.runLater(() -> {
                GamePage.getGP().createRack(rackLabels);
            });
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    //GETTES
    public LandingPage getLandingPage() {return landingPage;}


    private  static class ViewHolder{ public static final View v = new View();}
    public static View getView() {return ViewHolder.v;}

}