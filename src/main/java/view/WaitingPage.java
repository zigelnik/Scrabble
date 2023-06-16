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

    public void setWaitingDisplay() {
        if(isHost)
        {
            startButton.setOnAction(e -> {
                //TODO: do NOT change the methods call order!!
                gp.start(theStage);
                vm.m.host.gameState.initPlayers();
                View.getView().setPlayerHand();
//                View.getView().setViewModel();
                vm.initPlayersBoard();
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

    public void setClientBoard(){

    }


    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }

    private  static class WPHolder{ public static final WaitingPage wp = new WaitingPage();}
    public static WaitingPage getWP() {return WPHolder.wp;}
}