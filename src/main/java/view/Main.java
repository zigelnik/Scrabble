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
        Model m = new Model();// Model
        ViewModel vm = new ViewModel(m);//View model
        View v = new View(vm);//View

        v.getLandingPage().start(new Stage());

    }
}