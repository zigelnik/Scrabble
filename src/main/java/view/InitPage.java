package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class InitPage {

    private VBox root;
    private TextField playerNameField;
    private TextField ipAddressField;
    private Button startButton;
    private Button hostButton;

    public InitPage() {
        initializeUI();
    }

    private void initializeUI() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Scrabble Game Initialization");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label playerNameLabel = new Label("Player Name:");
        playerNameField = new TextField();

        Label ipAddressLabel = new Label("IP Address:");
        ipAddressField = new TextField();

        startButton = new Button("Start Game");
        hostButton = new Button("Host Game");

        root.getChildren().addAll(titleLabel, playerNameLabel, playerNameField, ipAddressLabel, ipAddressField, startButton, hostButton);
    }

    public VBox getUI() {
        return root;
    }

    public String getPlayerName() {
        return playerNameField.getText();
    }

    public String getIpAddress() {
        return ipAddressField.getText();
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getHostButton() {
        return hostButton;
    }
}
