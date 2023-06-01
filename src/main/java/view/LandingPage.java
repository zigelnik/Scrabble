package view;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LandingPage extends Application {

    @FXML
    private Button hostButton;

    @FXML
    private Button joinButton;

    @FXML
    private TextField nameField;


    public VBox root;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            loadFXML();
            Scene scene = new Scene(root, 400, 300);
            scene.getStylesheets().add(getClass().getResource("/gameGui.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadFXML() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(LandingPage.class.getResource("/fxmlFiles/landing-page.fxml"));
        root = fxmlLoader.load();
    }

    @FXML
    private void host() {
        String name = nameField.getText();
        // Perform actions when the host button is clicked, e.g., starting a host game
        System.out.println("Host button clicked. Name: " + name);
    }

    @FXML
    private void join() {
        String name = nameField.getText();
        // Perform actions when the join button is clicked, e.g., joining a game
        System.out.println("Join button clicked. Name: " + name);
    }
}
