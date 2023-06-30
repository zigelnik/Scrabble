package view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import view_model.ViewModel;

import java.util.*;
/**

 The View class represents the view component of the game GUI. It implements the Observer interface to receive updates from the model.
 It provides methods for setting up and updating the view based on the model's data.
 */
public class View implements Observer {
    LandingPage landingPage = new LandingPage();
    GamePage gamePage = GamePage.getGP();
    ViewModel vm = ViewModel.getViewModel();


    /**
     * Sets up the view model by binding the necessary properties between the view and the view model.
     */
    public void setViewModel() {
        vm.playerQuery.bind(gamePage.playerTmpQuery.textProperty());// Binding the ViewModel to View
        gamePage.scoreLabel.textProperty().bind(vm.score.asString()); // Binding the View to ViewModel
//        gamePage.updateBoard(vm.boardQuery.toString());
        gamePage.boardQuery.textProperty().bind(vm.boardQuery);
//        if(!vm.boardQuery.toString().equals("")){
//            gamePage.updateBoard(gamePage.boardQuery.textProperty().toString());
//        }
        //binding for the playerHand
        setPlayerHand();
    }

    /**
     * Sets the player's hand on the game page.
     */

    synchronized public void setPlayerHand(){
        int ind = 0;
        List<Label> rackLabels =Collections.synchronizedList(new ArrayList<>()); // tmprackLabels for the method createRack

        for (String strTile : vm.playerHand) {
            Label label = new Label(strTile);
            String tmpStr = vm.playerHand.get(ind);
            StringProperty strProperty = new SimpleStringProperty(tmpStr); //converting String to StringProperty
            label.textProperty().bindBidirectional(strProperty); //binding between label to StringProperty
            rackLabels.add(label);
            ind++;
        }
        Platform.runLater(()->{
           gamePage.createRack(rackLabels);
        });
    }

    /**
     * Updates the view when notified by the model.
     *
     * @param o   The observable object (model).
     * @param arg The argument passed by the model.
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    /**
     * Retrieves the landing page.
     *
     * @return The landing page.
     */
    //GETTES
    public LandingPage getLandingPage() {return landingPage;}

    /**
     * Static holder class for the View instance.
     */
    private  static class ViewHolder{ public static final View v = new View();}

    /**
     * Retrieves the View instance.
     *
     * @return The View instance.
     */
    public static View getView() {return ViewHolder.v;}

}