package view;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private void hostBtn() {
        String name = nameField.getText();
        while (name.isEmpty()) {
            showAlert("Error", "Don't forget to enter your name.");
            name = nameField.getText();
            
        }
        System.out.println("Host button clicked. Name: " + name);
    }

    @FXML
    private void joinBtn() {
        String name = nameField.getText();
        System.out.println("Join button clicked. Name: " + name);
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("We have a problem!");
        alert.setContentText(content);

        // Apply custom CSS styles to the dialog pane
        alert.getDialogPane().getStyleClass().add("my-dialog");

        // Apply custom CSS styles to the buttons
        alert.getDialogPane().getButtonTypes().forEach(buttonType -> {
            Button button = (Button)alert.getDialogPane().lookupButton(buttonType);
            button.getStyleClass().add("my-button");
        });

        alert.showAndWait();
    }
}
