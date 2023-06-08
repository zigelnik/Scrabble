package model.Test;

import model.concrete.Board;
import model.concrete.GameState;
import model.concrete.Player;
import model.concrete.Tile;

import java.util.List;

public class GameStateTest {

    private static GameState gameState;

    public void setup() {
        gameState = GameState.getGameState();
    }

    public void testGetBag() {
        Tile.Bag bag = gameState.getBag();
        assert bag != null;
    }

    public void testGetPlayersList() {
        List<Player> playersList = gameState.getPlayersList();
        assert playersList != null;
        assert playersList.size() == 0;
    }

    public void testGetBoard() {
        Board board = gameState.getBoard();
        assert board != null;
    }

    public void testGetIsGameOver() {
        boolean isGameOver = gameState.getIsGameOver();
        assert !isGameOver;
    }

    public  void testSetTurns() {
        Player p = new Player();
        Player p2 = new Player();
        Player p3 = new Player();
        p.setName("tal");
        p2.setName("Joe");
        p3.setName("ZigBig");
        gameState.getPlayersList().add(p);
        gameState.getPlayersList().add(p2);
        gameState.getPlayersList().add(p3);

        gameState.setTurns();
    }



    public void testAddPlayer() {
        Player player = new Player();
        gameState.addPlayer(player);

        List<Player> playersList = gameState.getPlayersList();
        assert playersList.size() == 1;
        assert playersList.get(0) == player;
    }
    public void testIsWinner() {
        // Set up players
        Player p = new Player();
        Player p2 = new Player();
        Player p3 = new Player();
        p.setName("tal");
        p2.setName("Joe");
        p3.setName("ZigBig");
        p.setSumScore(100);
        p.setHandSize(0);
        p2.setSumScore(75);
        p2.setHandSize(0);
        p3.setSumScore(62);
        p3.setHandSize(0);

//        gameState.playersList.add(p);
//        gameState.playersList.add(p2);
//        gameState.playersList.add(p3);
        gameState.isWinner();


    }
    public static void main(String[] args) {
        GameStateTest tester = new GameStateTest();
        tester.setup();
        tester.testGetBag();
        tester.testGetBoard();
        tester.testAddPlayer();
        tester.testGetPlayersList();
        tester.testSetTurns();
        tester.testGetIsGameOver();
        tester.testIsWinner();
        // Call other test methods here...
    }
}


