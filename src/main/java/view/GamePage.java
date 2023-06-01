package view;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GamePage extends Application {

    @FXML
    private GridPane gameBoard;

    @FXML
    private VBox playersVBox;

    @FXML
    private Button quitButton;

    @FXML
    private Button passButton;

    @FXML
    private Button challengeButton;

    public VBox root;

    private static final String[][] BOARD_LAYOUT = {
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
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadFXML();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/gameGui.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Scrabble Game");
        primaryStage.show();

    }

    private void loadFXML() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/game-page.fxml"));
        root = fxmlLoader.load();
    }


    private void initializeGameBoard() {
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
    @FXML
    private void quit() {
        // Perform actions when the quit button is clicked
        System.out.println("Quit button clicked");
    }

    @FXML
    private void pass() {
        // Perform actions when the pass button is clicked
        System.out.println("Pass button clicked");
    }

    @FXML
    private void challenge() {
        // Perform actions when the challenge button is clicked
        System.out.println("Challenge button clicked");
    }

}


//import javafx.application.Application;
//        import javafx.geometry.Pos;
//        import javafx.scene.Scene;
//        import javafx.scene.control.Button;
//        import javafx.scene.control.Label;
//        import javafx.scene.control.TextField;
//        import javafx.scene.layout.GridPane;
//        import javafx.scene.layout.HBox;
//        import javafx.scene.layout.VBox;
//        import javafx.scene.paint.Color;
//        import javafx.stage.Stage;
//
//public class ScrabbleGUI extends Application {
//
//
//    private TextField tileInput;
//    private TextField rowInput;
//    private TextField colInput;
//    private CheckBox verticalCheckbox;
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//
//
//        // Tile input field
//        tileInput = new TextField();
//        tileInput.setPromptText("Enter a tile");
//
//        // Row input field
//        rowInput = new TextField();
//        rowInput.setPromptText("Row");
//
//        // Column input field
//        colInput = new TextField();
//        colInput.setPromptText("Column");
//
//        // Vertical checkbox
//        verticalCheckbox = new CheckBox("Vertical");
//
//        // Place tile button
//        Button placeTileButton = new Button("Place Tile");
//        placeTileButton.setOnAction(e -> {
//            int row = Integer.parseInt(rowInput.getText());
//            int col = Integer.parseInt(colInput.getText());
//            boolean isVertical = verticalCheckbox.isSelected();
//            placeTile(row, col, tileInput.getText(), isVertical);
//        });
//
//        // HBox for tile input and button
//        HBox tileInputBox = new HBox(10);
//        tileInputBox.setAlignment(Pos.CENTER);
//        tileInputBox.getChildren().addAll(tileInput, rowInput, colInput, verticalCheckbox, placeTileButton);
//
//        // VBox for game board and input box
//        VBox root = new VBox(10);
//        root.setAlignment(Pos.CENTER);
//        root.getChildren().addAll(gameBoard, tileInputBox);
//
//        Scene scene = new Scene(root, 600, 600);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//
//    private void placeTile(int row, int col, String tile, boolean isVertical) {
//        Label cellLabel = (Label) gameBoard.getChildren().stream()
//                .filter(node -> GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
//                .findFirst().orElse(null);
//
//        if (cellLabel != null) {
//            cellLabel.setText(tile);
//            if (isVertical) {
//                cellLabel.setRotate(90); // Rotate the tile label to indicate vertical placement
//            } else {
//                cellLabel.setRotate(0); // Reset rotation for horizontal placement
//            }
//        }
//
//
//    }
//
//}