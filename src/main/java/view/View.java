package view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import view_model.ViewModel;

import java.util.*;

public class View implements Observer {
    LandingPage landingPage = new LandingPage();
    GamePage gamePage = GamePage.getGP();
    ViewModel vm = ViewModel.getViewModel();

    public void setViewModel() {
        vm.playerQuery.bind(gamePage.playerTmpQuery.textProperty());// Binding the ViewModel to View
        gamePage.scoreLabel.textProperty().bind(vm.score.asString()); // Binding the View to ViewModel
        //binding for the playerHand
        setPlayerHand();
    }

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

    @Override
    public void update(Observable o, Object arg) {

    }

    //GETTES
    public LandingPage getLandingPage() {return landingPage;}


    private  static class ViewHolder{ public static final View v = new View();}
    public static View getView() {return ViewHolder.v;}

}