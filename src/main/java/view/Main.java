package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import view_model.ViewModel;

public class Main extends Application {
    @Override
    public void start(Stage stage)   {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 200, 200);
            //Model m = new Model("properties.txt");
            //WindowController wc = fxmlLoader.getController();
           // ViewModel vm = new ViewModel(m);


            stage.setTitle("hello");
            stage.setScene(scene);

            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}