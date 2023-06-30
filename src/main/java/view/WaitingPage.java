package view;

import javafx.application.Application;
import javafx.application.Platform;
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

import java.util.Objects;


/**

 The WaitingPage class represents the waiting page of the game GUI.
 It extends the JavaFX Application class and provides methods for setting up and displaying the waiting page.
 */
public class WaitingPage extends Application {

    public ViewModel vm = ViewModel.getViewModel();
    public GamePage gp = GamePage.getGP();

    public View v = View.getView();
    public static Stage theStage;
    @FXML
    private Label waitingLabel;
    @FXML
    private Button startButton;

    public VBox root;
    private boolean isHost = false;


    /**
     * Starts the waiting page by setting up the stage and initializing the scene.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     * @throws Exception if an error occurs during the initialization of the waiting page.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        theStage = primaryStage;
        root = new VBox(10);
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gameGui.css")).toExternalForm());
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
        startButton.setVisible(isHost);
        primaryStage.show();
        setWaitingDisplay();
    }

    /**
     * Sets the waiting display and configures the start button's action event.
     */
    public void setWaitingDisplay() {
        if(isHost)
        {
            startButton.setOnAction(e -> {
                //TODO: do NOT change the methods call order!!
                gp.start(theStage);
                vm.initPlayersBoard();
                View.getView().setViewModel();
                vm.m.host.gameState.initPlayers();
                Thread t = new Thread(() -> {
                    vm.m.host.gameState.initGame();
                });
                t.start();
            });
        }
//        else
//        {
//            View.getView().setViewModel();
//        }
    }

    /**
     * Sets whether the current instance is the host.
     *
     * @param isHost true if the current instance is the host, false otherwise.
     */
    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }

    /**
     * Static holder class for the WaitingPage instance.
     */
    private  static class WPHolder{ public static final WaitingPage wp = new WaitingPage();}

    /**
     * Retrieves the WaitingPage instance.
     *
     * @return The WaitingPage instance.
     */
    public static WaitingPage getWP() {return WPHolder.wp;}
}