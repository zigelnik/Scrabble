package view;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class LandingPage extends Application {


    private String hostPort;
    private String playerName;


    private String IP;

    private static Stage theStage;
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
            theStage = primaryStage;
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
    public void hostBtn() {
        String name = nameField.getText();
        if(name.isEmpty()) {
            showAlert("Error", "Don't forget to enter your name.");
            name = nameField.getText();
        }
        else{
            System.out.println("Host button clicked. Name: " + name);
            Label title = new Label("Scrabble Game");
            title.getStyleClass().add("title-label");
            Label portLabel = new Label("Port:");
            TextField portField = new TextField();
            portField.setPromptText("Enter Port number");
            Button startButton = new Button("Start Game");


            // Set event handler for startButton
            startButton.setOnAction(event -> {
                String port = portField.getText();
                if(isPortValid(port)){
                    System.out.println("Host button clicked." + " Port: " + port);
                   this.hostPort = port;
                }

            });

            // Create the game settings layout
            VBox gameSettingsLayout = new VBox(10);
            gameSettingsLayout.setAlignment(Pos.CENTER);
            gameSettingsLayout.getChildren().addAll(title,portLabel, portField, startButton);

            // Create a new scene with the game settings layout
            Scene initPortWindow = new Scene(gameSettingsLayout, 400, 300);
            initPortWindow.getStylesheets().add(getClass().getResource("/gameGui.css").toExternalForm());


            // Set the scene on the existing stage, effectively replacing the current content with the game settings page
            getPrimaryStage().setScene(initPortWindow);


            //TODO: how to call to host method from View


//            WaitingPage wp = new WaitingPage();
//            try {
//                wp.start(getPrimaryStage());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
        }

    }

    @FXML
    public void joinBtn() {
        String name = nameField.getText();
        if(name.isEmpty()) {
            showAlert("Error", "Don't forget to enter your name.");
            name = nameField.getText();
        }
        else{
            System.out.println("Join button clicked. Name: " + name);
            Label title = new Label("Scrabble Game");
            title.getStyleClass().add("title-label");
            Label portLabel = new Label("Port:");
            TextField portField = new TextField();
            portField.setPromptText("Enter Port number");
            Label ipLabel = new Label("IP:");
            TextField ipField = new TextField();
            ipField.setPromptText("Enter IP number");
            Button startButton = new Button("Start Game");


            // Set event handler for startButton
            startButton.setOnAction(event -> {
                String port = portField.getText();
                String ip = ipField.getText();

                if(isiPValid(ip)&&isPortValid(port)){
                    System.out.println("Host button clicked." + " Port: " + port +  " IP: " + ip);
                    this.IP = ip;
                }

            });

            // Create the game settings layout
            VBox gameSettingsLayout = new VBox(10);
            gameSettingsLayout.setAlignment(Pos.CENTER);
            gameSettingsLayout.getChildren().addAll(title,ipLabel,ipField,portLabel, portField, startButton);

            // Create a new scene with the game settings layout
            Scene initPortAndIpWindow = new Scene(gameSettingsLayout, 400, 300);
            initPortAndIpWindow.getStylesheets().add(getClass().getResource("/gameGui.css").toExternalForm());


            // Set the scene on the existing stage, effectively replacing the current content with the game settings page
            getPrimaryStage().setScene(initPortAndIpWindow);

//            WaitingPage wp = new WaitingPage();
//            try {
//                wp.start(getPrimaryStage());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
        }

    }

    public boolean isPortValid(String port){
        try {
            if (port.isEmpty()) {
                throw new IllegalArgumentException("Port number cannot be empty.");
            }
//            if (!port.equals(hostPort)) {
//                throw new IllegalArgumentException("No such server port");
//            }
            int portNum = Integer.parseInt(port);

            if (portNum < 1024 || portNum > 65535) {
                throw new IllegalArgumentException("Port number should be between 1024 and 65535.");
            }

            System.out.println("Port number:" + port);
        } catch (NumberFormatException e) {
            showAlert("Invalid Port","Invalid port number entered. Please enter a valid numeric value.");
            return false;
        } catch (IllegalArgumentException e) {
            showAlert("Invalid Port",e.getMessage());
            return false;
        }

        return true;
    }
    public boolean isiPValid(String ip) {
        try {
            if (ip.isEmpty()) {
                throw new IllegalArgumentException("IP number cannot be empty.");
            }
            String IP_PATTERN = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

            Pattern pattern = Pattern.compile(IP_PATTERN);

                if(!pattern.matcher(ip).matches()) {
                    throw new IllegalArgumentException("Invalid IP address entered. Please enter a valid IP address.");
                }

        }catch (IllegalArgumentException e) {
            showAlert("Invalid IP",e.getMessage());
            return false;
        }
        return true;
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
    public static Stage getPrimaryStage() {return theStage;}
    public String getPlayerName() {return playerName;}
   public String getHostPort() {return hostPort;}
    public String getIP() {
        return IP;
    }
}
