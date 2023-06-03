package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WaitingPage extends Application {

    @FXML
    private Label waitingLabel;

    @FXML
    private Button startButton;

    public VBox root;

    private boolean isHost = false;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new VBox(10);
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/gameGui.css").toExternalForm());
        primaryStage.setScene(scene);
        if (isHost) {
            waitingLabel = new Label("Waiting for Players to Join");
            startButton = new Button("Start Game");
            setHostDisplay();
        } else {
            waitingLabel = new Label("Waiting for Host to Start");
            startButton = new Button("Join Game");
            setJoinDisplay();
        }
        waitingLabel.getStyleClass().add("waiting-label");
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(waitingLabel,startButton);
        primaryStage.show();
    }

    public void setHostDisplay() {
        startButton.setOnAction(e -> {
            // Handle the start game functionality
            System.out.println("Start button clicked - Host mode");
        });
    }

    public void setJoinDisplay() {
        startButton.setOnAction(e -> {
            // Handle the join game functionality
            System.out.println("Join button clicked - Join mode");
        });
    }

    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }
}