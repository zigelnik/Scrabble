package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view_model.ViewModel;

public class Main extends Application  {


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Model m = Model.getModel();// Model
        View v = View.getView(); //View
        ViewModel vm = ViewModel.getViewModel(); //View model
        m.addObserver(vm);
        v.getLandingPage().start(primaryStage);
        vm.addObserver(v);

    }
}