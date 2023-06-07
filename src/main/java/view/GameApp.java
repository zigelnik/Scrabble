package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Model;

public class GameApp extends Application {

    private Stage primaryStage;

    private Model m;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        m = new Model();
        showInitPage();
    }

    private void showInitPage() {
        InitPage initPage = new InitPage();

        Button startButton = initPage.getStartButton();
        startButton.setOnAction(event -> {
            String playerName = initPage.getPlayerName();
            String ipAddress = initPage.getIpAddress();

            Thread joinThread = new Thread(()-> {
                m.joinGame("localhost", 9996);
            });
            joinThread.start();


            // Show the game page
            showGamePage();
        });

        Button HostButton = initPage.getHostButton();
        HostButton.setOnAction(event -> {
            Thread hostThread = new Thread(() -> {
                m.hostGame(9996);
            });
            hostThread.start();
            showGamePage();
        });

        Scene scene = new Scene(initPage.getUI(), 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Scrabble Game - Init Page");
        primaryStage.show();
    }

    private void showGamePage() {
        GamePage gp = new GamePage();
        gp.start(new Stage());
        primaryStage.setTitle("Scrabble Game");
        primaryStage.show();
    }
}
