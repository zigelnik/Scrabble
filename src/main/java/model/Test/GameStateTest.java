package model;

import model.concrete.Board;
import model.concrete.Tile;

import java.util.ArrayList;
import java.util.List;

public class GameStateTest {

    private GameState gameState;

    public void setup() {
        gameState = new GameState();
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
        gameState.playersList.add(p);
        gameState.playersList.add(p2);
        gameState.playersList.add(p3);

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
        p.sumScore = 100;
        p.handSize = 0;
        p2.sumScore = 75;
        p2.handSize = 0;
        p3.sumScore = 62;
        p3.handSize = 0;

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


