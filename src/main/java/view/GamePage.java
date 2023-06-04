package view;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GamePage extends Application {

    private static Stage theStage;
    private GridPane gameBoard;
    private CheckBox verticalCheckbox;
    private Label tilesLabel;
    private Label scoreLabel;

    private static String[][] BOARD_LAYOUT = {
            {"3W", " ", " ", "2L", " ", " ", " ", "3W", " ", " ", " ", "2L", " ", " ", "3W"},
            {" ", "2W", " ", " ", " ", "3L", " ", " ", " ", "3L", " ", " ", " ", "2W", " "},
            {" ", " ", "2W", " ", " ", " ", "2L", " ", "2L", " ", " ", " ", "2W", " ", " "},
            {"2L", " ", " ", "2W", " ", " ", " ", "2L", " ", " ", " ", "2W", " ", " ", "2L"},
            {" ", " ", " ", "2L", "2W", " ", " ", " ", " ", " ", "2W", " ", " ", " ", "3W"},
            {" ", "3L", " ", " ", " ", "3L", " ", " ", " ", "3L", " ", " ", " ", "3L", " "},
            {" ", " ", "2L", " ", " ", " ", "2L", " ", "2L", " ", " ", " ", "2L", " ", " "},
            {"3W", " ", " ", "2L", " ", " ", " ", "*", " ", " ", " ", "2L", " ", " ", "3W"},
            {" ", " ", "2L", " ", " ", " ", "2L", " ", "2L", " ", " ", " ", "2L", " ", " "},
            {" ", "3L", " ", " ", " ", "3L", " ", " ", " ", "3L", " ", " ", " ", "3L", " "},
            {" ", " ", " ", " ", "2W", " ", " ", " ", " ", " ", "2W", " ", " ", " ", " "},
            {"2L", " ", " ", "2W", " ", " ", " ", "2L", " ", " ", " ", "2W", " ", " ", "2L"},
            {" ", " ", "2W", " ", " ", " ", "2L", " ", "2L", " ", " ", " ", "2W", " ", " "},
            {" ", "2W", " ", " ", " ", "3L", " ", " ", " ", "3L", " ", " ", " ", "2W", " "},
            {"3W", " ", " ", "2L", " ", " ", " ", "3W", " ", " ", " ", "2L", " ", " ", "3W"},
    };

    public static void main(String[] args) {
        launch(args);
    }

    public void initBoard(){
        // Game board
        gameBoard = new GridPane();
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);

        // Create the game board based on the layout
        for (int row = 0; row < BOARD_LAYOUT.length; row++) {
            for (int col = 0; col < BOARD_LAYOUT[row].length; col++) {
                String cellValue = BOARD_LAYOUT[row][col];
                Color cellColor = getColorForCell(cellValue);

                Label cellLabel = createCellLabel(cellValue, cellColor);
                gameBoard.add(cellLabel, col, row);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        theStage = primaryStage;
        primaryStage.setTitle("Scrabble Game");

        initBoard();

        // Tiles label
        tilesLabel = new Label("Player Tiles");
        tilesLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Score label
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Buttons
        Button passButton = new Button("Pass");
        Button quitButton = new Button("Quit");
        Button challengeButton = new Button("Challenge");


        // Button event handlers
        passButton.setOnAction(e -> {
            System.out.println("Pass button pressed");
            // Add your desired functionality here
        });

        quitButton.setOnAction(e -> {
            System.out.println("Quit button pressed");
            // Add your desired functionality here
        });

        challengeButton.setOnAction(e -> {
            System.out.println("Challenge button pressed");
            // Add your desired functionality here
        });


        // Vertical checkbox
        verticalCheckbox = new CheckBox("Vertical");

        //TODO:how to drag tiles to board and get the word tiles
        // Place tile button
//        Button placeTileButton = new Button("Place Tile");
//        placeTileButton.setOnAction(e -> {
//            int row = Integer.parseInt(rowInput.getText());
//            int col = Integer.parseInt(colInput.getText());
//            boolean isVertical = verticalCheckbox.isSelected();
//            placeTile(row, col, tileInput.getText(), isVertical);
//        });

        // HBox for tile input and button
        HBox labelsBox = new HBox(10);
        labelsBox.setAlignment(Pos.CENTER);
        labelsBox.getChildren().addAll(scoreLabel,tilesLabel,verticalCheckbox);

        // HBox for buttons
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(passButton,challengeButton,quitButton);

        // VBox for game board and input box
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gameBoard, labelsBox,buttonsBox);
        initGameDisplay(root);

    }

    public void initGameDisplay(VBox root){
        Scene scene = new Scene(root, 600, 600);
        theStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/gameGui.css").toExternalForm());
        theStage.show();
    }

    private Label createCellLabel(String cellValue, Color cellColor) {
        Label cellLabel = new Label(cellValue);
        cellLabel.setPrefSize(40, 40);
        cellLabel.setAlignment(Pos.CENTER);
        cellLabel.setStyle("-fx-background-color: " + toRGBCode(cellColor) + "; -fx-text-fill: black");
        return cellLabel;
    }

    private Color getColorForCell(String cellValue) {
        switch (cellValue) {
            case "2L":
                return Color.ORANGE;
            case "3L":
                return Color.DARKBLUE;
            case "2W":
                return Color.LIGHTPINK;
            case "3W":
                return Color.DARKRED;
            case "*":
                return Color.YELLOW;
            default:
                return Color.LIGHTGRAY;
        }
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void placeTile(int row, int col, String tile, boolean isVertical) {
        Label cellLabel = (Label) gameBoard.getChildren().stream()
                .filter(node -> GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                .findFirst().orElse(null);

        if (cellLabel != null) {
            cellLabel.setText(tile);
            if (isVertical) {
                cellLabel.setRotate(90); // Rotate the tile label to indicate vertical placement
            } else {
                cellLabel.setRotate(0); // Reset rotation for horizontal placement
            }
        }
    }



}