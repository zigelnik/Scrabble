package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view_model.ViewModel;

public class Main  {
    public static void main(String[] args) {
        Model m = new Model();// Model
        ViewModel vm = new ViewModel(m);//View model
        LandingPage entryPage = new LandingPage();
        WaitingPage waitingPage = new WaitingPage();
        GamePage gamePage = new GamePage();


        WindowController wc = new WindowController(vm,entryPage,waitingPage,gamePage);//View



    }
}