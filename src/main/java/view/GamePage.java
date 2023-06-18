package view;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import view_model.ViewModel;

import java.util.*;

public class GamePage extends Application {

    private static Stage theStage;
    public final ObservableList<String> placedTiles = FXCollections.observableArrayList();
    private static Map<String , Point2D> map = Collections.synchronizedMap(new HashMap<>()); //map between letter and coordinate on gameBoard
    public GridPane playerRack;
    public Label  scoreLabel = new Label("0");
    public Label playerTmpQuery = new Label();
    public Label boardQuery = new Label();
    private final Object lockObject = new Object();



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


    List<Label> list = Collections.synchronizedList(new ArrayList<>(Collections.nCopies(7, new Label(""))));
    GridPane gameBoard = new GridPane();



    @Override
    public void start(Stage primaryStage) {
        theStage = primaryStage;
        primaryStage.setTitle("Scrabble Game");

        // Game board
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);

        // Create the game board based on the layout
        for (int row = 0; row < BOARD_LAYOUT.length; row++) {
            for (int col = 0; col < BOARD_LAYOUT[row].length; col++) {
                String cellValue = BOARD_LAYOUT[row][col];
                Color cellColor = getColorForCell(cellValue);

                Label cellLabel = createCellLabel(cellValue, cellColor);
                enableDropOnCell(cellLabel);
                gameBoard.add(cellLabel, col, row);
            }
        }

        // Score label

        scoreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        scoreLabel.setAlignment(Pos.BOTTOM_CENTER);
        Label scoreTitle = new Label("Score: ");
        scoreTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");


        // Vertical checkbox
        CheckBox verticalCheckBox = new CheckBox("Vertical");
        verticalCheckBox.setAlignment(Pos.BOTTOM_RIGHT);
        verticalCheckBox.setTranslateY(-4); // Move the CheckBox 10 pixels up

        // Button: Pass
        Button passButton = new Button("Pass");
        passButton.setOnAction(event -> {
            // Handle pass button action
        });


        // Button: Challenge
        Button challengeButton = new Button("Challenge");
        challengeButton.setOnAction(event -> {
            // Handle challenge button action
        });

        // Button: Submit
        Button subButton = new Button("Submit");
        subButton.setOnAction(event -> {
            // Handle pass button action
            // Check if placedTiles is empty
            if (placedTiles.isEmpty()) {
                // Alert the player that no tiles are placed
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Tiles Placed");
                alert.setHeaderText(null);
                alert.setContentText("You haven't placed any tiles on the board.\nTry to place tiles or pass your turn ! ");
                alert.showAndWait();
                return; // Return without further processing
            }

            //sort the tiles by their location on board
            Comparator<String> tileComparator = Comparator.comparingInt(tile -> {
                Point2D location = map.get(tile);
                if (location != null) {
                    return (int) (location.getX() + location.getY());
                }
                return 0;
            });
            placedTiles.sort(tileComparator);

            //get first tile coordinates
            int row = (int) Math.round(map.get(placedTiles.get(0)).getX());
            int col = (int) Math.round(map.get(placedTiles.get(0)).getY());

            //get the decision vertical or not
            boolean vertical = verticalCheckBox.isSelected();

            //creates the string which represent the query that we want to send to hostPlayer
            StringBuilder sb = new StringBuilder();
            for(String str : placedTiles){
                sb.append(str);
            }
            sb.append(",").append(row).append(",").append(col);
            sb.append(",").append(vertical);
            String playerQuery = sb.toString();


            System.out.println("Player Query is: " + playerQuery);
            synchronized (lockObject) {
                playerTmpQuery.setText(playerQuery);
                Model.getModel().updateQuery(playerQuery);  // updating when something changes
                lockObject.notify(); // Notifies the waiting thread to resume
            }

            //TODO:Get the response from HostPlayer if move is valid
            //if the move is not valid we need to remove the tiles from board and get it back to player hand
            //moveBackPlacedTiles();

            //reset the placedTiles list for the next turn

            placedTiles.clear();
            map.clear();
        });

        // Button: Quit
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(event -> {
            // Handle quit button action
        });

        // HBox for buttons
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);
        buttonContainer.getChildren().addAll(passButton, challengeButton, quitButton,subButton , verticalCheckBox);

        // HBox for score label and checkbox
        HBox topContainer = new HBox(10);
        topContainer.setAlignment(Pos.BOTTOM_CENTER);
        topContainer.getChildren().addAll(scoreTitle,scoreLabel);

        // VBox for game board and buttons
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().addAll(topContainer, gameBoard, buttonContainer);

        Scene scene = new Scene(root, 600, 650);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/gameGui.css")).toExternalForm());
        primaryStage.setScene(scene);

        playerRack = new GridPane();
        playerRack.setHgap(5);
        playerRack.setVgap(5);
        playerRack.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().add(playerRack);
        // creating initial List that contains only X for playerRack not null, the initPack will override

        createRack(list);
        primaryStage.show();
    }


    public void updateBoard(String message){
        char[] ch = message.split(",")[0].toCharArray();
        int row = Integer.parseInt(message.split(",")[1]);
        int col = Integer.parseInt(message.split(",")[2]);
        String isVert = message.split(",")[3];

        if(isVert.equals("TRUE")){
            for(int i = 0; i < ch.length; i++){
                placeTileOnBoard(row + i,col,Character.toString(ch[i]));
            }
        }
        else{
            for(int i = 0; i < ch.length; i++){
                placeTileOnBoard(row,col+i,Character.toString(ch[i]));
            }
        }
    }
    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public void placeTileOnBoard(int row, int col, String value) {
        Node existingNode = getNodeFromGridPane(gameBoard, col, row);
        Label cellLabel = (Label) existingNode;
        if(cellLabel!=null) {
            cellLabel.setText(value);
        }
    }

    public String getPlayerQuery(){
       return playerTmpQuery.getText();
    }

     public void createRack(List<Label> list){

        //Create the player rack tiles
        int i =0;
        for (Label lb : list) {
            Label tileLabel = createTileLabel(lb.getText(), Color.LIGHTSALMON);
            enableDrag(tileLabel);
            playerRack.add(tileLabel, i, 0);
            i++;
        }
    }



    private Label createCellLabel(String cellValue, Color cellColor) {
        Label cellLabel = new Label(cellValue);
        cellLabel.setPrefSize(40, 40);
        cellLabel.setAlignment(Pos.CENTER);
        cellLabel.setStyle("-fx-background-color: " + toRGBCode(cellColor) + "; -fx-text-fill: black; -fx-font-weight: bold;");
        return cellLabel;
    }

    private Label createTileLabel(String tileValue, Color tileColor) {
        Label tileLabel = new Label(tileValue);
        tileLabel.setPrefSize(40, 40);
        tileLabel.setAlignment(Pos.CENTER);
        tileLabel.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-weight: bold;");
        return tileLabel;
    }


    private void enableDropOnCell(Label cellLabel) {
        String originalCellValue = cellLabel.getText();
        Color originalCellColor = getColorForCell(originalCellValue);

        cellLabel.setOnDragOver(event -> {
            if (event.getGestureSource() != cellLabel && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        cellLabel.setOnDragEntered(event -> {
            if (event.getGestureSource() != cellLabel && event.getDragboard().hasString()) {
                cellLabel.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-weight: bold;");
            }
            event.consume();
        });

        cellLabel.setOnDragExited(event -> {
            cellLabel.setStyle("-fx-background-color: " + toRGBCode(originalCellColor) + "; -fx-text-fill: black; -fx-font-weight: bold;");
            event.consume();
        });

        cellLabel.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String tile = db.getString();
                if (cellLabel.getText().equals(tile)) {
                    cellLabel.setText("");
                    updatePlayerRack(tile);
                    success = true;
                } else {
                    cellLabel.setText(tile);
                    success = true;

                    Point2D coordinates = new Point2D(GridPane.getRowIndex(cellLabel), GridPane.getColumnIndex(cellLabel));
                    map.put(tile, coordinates);
                }

                if (isSpecialCell(cellLabel)) {
                    // Restore the original color of the special cell
                    cellLabel.setStyle("-fx-background-color: " + toRGBCode(originalCellColor) + "; -fx-text-fill: black; -fx-font-weight: bold;");
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        cellLabel.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                String tile = cellLabel.getText();
                if (!tile.isEmpty()) {
                    placedTiles.remove(cellLabel.getText());
                    map.remove(cellLabel.getText());
                    cellLabel.setText("");
                    updatePlayerRack(tile);
                }
            }
        });
    }


    private void enableDrag(Label tileLabel) {
        tileLabel.setOnDragDetected(event -> {
            Dragboard db = tileLabel.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(tileLabel.getText());
            db.setContent(content);
            // Set a snapshot of the tile as a drag view
            db.setDragView(tileLabel.snapshot(null, null));
            event.consume();
        });
        tileLabel.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                placedTiles.add(tileLabel.getText());
                tileLabel.setText("");
            }
            event.consume();
        });
    }
    private void updatePlayerRack(String tile) {
        // Find the tile label in the player rack
        for (Node node : playerRack.getChildren()) {
            if (node instanceof Label tileLabel) {
                if (tileLabel.getText().isEmpty()) {
                    // Found an empty slot in the player rack, place the tile
                    tileLabel.setText(tile);
                    enableDrag(tileLabel);
                    break;
                }
            }
        }
    }

    public void initPlayerRack(String rack){
        String [] arr = rack.split(",");
        for(int i = 0; i < 7; i++){
            updatePlayerRack(arr[i]);
        }
    }

    private boolean isSpecialCell(Label cellLabel) {
        String cellValue = cellLabel.getText();
        return cellValue.equals("2W") || cellValue.equals("3W") || cellValue.equals("2L") || cellValue.equals("3L") || cellValue.equals("*");
    }
    private Color getColorForCell(String cellValue) {
        return switch (cellValue) {
            case "2W" -> Color.LIGHTBLUE;
            case "3W" -> Color.BLUE;
            case "2L" -> Color.LIGHTPINK;
            case "3L" -> Color.RED;
            case "*" -> Color.YELLOW;
            default -> Color.WHITE;
        };
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public void changeCell(String cellValue, int row, int col) {
        Label cellLabel = createCellLabel(cellValue, getColorForCell(cellValue));
        enableDropOnCell(cellLabel);
        gameBoard.add(cellLabel, col, row);
    }

    public static Stage getTheStage() {
        return theStage;
    }
    public void moveBackPlacedTiles() {
        for (String tile : placedTiles) {
            Point2D coordinates = map.get(tile);
            if (coordinates != null) {
                int row = (int) coordinates.getX();
                int col = (int) coordinates.getY();
                Node cell = getNodeByRowColumnIndex(row, col, gameBoard);
                if (cell instanceof Label) {
                    ((Label) cell).setText("");
                }
            }
        }
    }

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    private  static class GPHolder{ public static final GamePage gp = new GamePage();}
    public static GamePage getGP() {return GPHolder.gp;}
    public Object getLockObject() {
        return lockObject;
    }
}