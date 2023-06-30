package view_model;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import model.network.GameServer;
import view.View;;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
/**

 The ViewModel class represents the view model component of the game GUI. It implements the Observer interface to receive updates from the model.
 It acts as an intermediary between the model and the view, providing data binding and updating the view based on the model's state.
 */
public class ViewModel extends Observable implements Observer {
    public Model m = Model.getModel();
    public IntegerProperty score;
    public StringProperty playerQuery;
    public ListProperty<String> playerHand;
    public StringProperty boardQuery;


    /**
     * Constructs a ViewModel object and initializes its properties.
     */

    public ViewModel() {
        this.score = new SimpleIntegerProperty(0);
        this.playerHand = new SimpleListProperty<>();
        this.playerQuery = new SimpleStringProperty("");
        this.boardQuery = new SimpleStringProperty("");
    }

    /**
     * Starts hosting a game with the specified port and name.
     *
     * @param port The port number for hosting the game.
     * @param name The name of the host.
     */
    public void hostGame(int port, String name) {
        m.hostGame(port, name);
    }

    /**
     * Joins a game with the specified IP, port, and name.
     *
     * @param ip   The IP address of the host.
     * @param port The port number for joining the game.
     * @param name The name of the player joining the game.
     */

    public void joinGame(String ip, int port, String name) {
        m.joinGame(ip, port, name);
    }

    /**
     * Call for Initializing the players' boards in the game.
     */
    public void initPlayersBoard() {
        GameServer.broadcastToClients("/start");
    }

    /**
     * Updates the view model when notified by the model.
     *
     * @param o   The observable object (model).
     * @param arg The argument passed by the model.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == m) {
            Platform.runLater(() -> {
                score.set(m.getPlayersScoreMap().get(m.playerId));

            });
            playerHand.set(FXCollections.observableList(m.getPlayersHandMap().get(m.playerId)));
            View.getView().setPlayerHand();
            // converting the m.getPlayerHand() to observableList (Only way to make apply the set)
            playerQuery.unbind();
            playerQuery.set(m.getPlayerQuery());
            boardQuery.set(m.boardQuery);
            //rendering the board after placing Tiles



        }
    }


    //GETTERS
    /**
     * Retrieves the model.
     *
     * @return The model instance.
     */
        public Model getModel () {
            return m;
        }

    /**
     * Retrieves the score property.
     *
     * @return The score property.
     */
        public int getScore () {
            return score.get();
        }
    /**
     * Static holder class for the ViewModel instance.
     */
        private static class ViewModelHolder {
            public static final ViewModel vm = new ViewModel();
        }
    /**
     * Retrieves the ViewModel instance.
     *
     * @return The ViewModel instance.
     */
        public static ViewModel getViewModel () {
            return ViewModelHolder.vm;
        }

}
