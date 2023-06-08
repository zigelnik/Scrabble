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
import model.Model;
import view_model.ViewModel;

public class WaitingPage extends Application {

ViewModel vm = ViewModel.getViewModel();
    private static GamePage gp = new GamePage();

    public static Stage theStage;
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
        theStage = primaryStage;
        root = new VBox(10);
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/gameGui.css").toExternalForm());
        primaryStage.setScene(scene);
        if (isHost) {
            waitingLabel = new Label("Waiting for Players to Join");
            startButton = new Button("Start Game");

        } else {
            waitingLabel = new Label("Waiting for Host to Start");
            startButton = new Button("Join Game");
        }
        waitingLabel.getStyleClass().add("waiting-label");
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(waitingLabel,startButton);
        startButton.setVisible(isHost ? true : false);
        primaryStage.show();
        setWaitingDisplay();
    }

    public void setWaitingDisplay() {
        startButton.setOnAction(e -> {
            if(isHost) {
                gp.start(theStage);
                vm.getModel().getHostServer().hostPlayer.initGame();
                vm.initPlayersBoard();
            }

        });
    }


    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }
}