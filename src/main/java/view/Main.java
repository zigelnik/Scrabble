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
        ViewModel vm = ViewModel.getViewModel();//View model
        m.addObserver(vm);
        View v = new View();//View
        v.getLandingPage().start(new Stage());
        vm.addObserver(v);

    }
}