package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WaitingPage extends Application {

    @FXML
    private Label waitingLabel;

    public VBox root;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadFXML();
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/gameGui.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadFXML() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/waiting-page.fxml"));
        root = fxmlLoader.load();
    }

    public void setMessage(String message) {
        waitingLabel.setText(message);
    }
}
