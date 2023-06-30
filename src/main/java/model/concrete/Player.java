package model.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Player {

    // data members //
    String playerName;
    int id;
    List<Tile> playerHand = Collections.synchronizedList(new ArrayList<>());
    int handSize; // physical size of tiles
    int sumScore;
    boolean isTurnOver;
    String acceptedQuery;

    /**
     * Constructs a new Player object with default values.
     * The id is set to 0, hand size to 7, sum score to 0, and isTurnOver to false.
     */
    public Player() {
        this.id = 0;
        this.handSize = 7;
        this.sumScore = 0;
        isTurnOver = false;
    }

    // Setters

    /**
     * Sets the sum score of the player.
     *
     * @param sumScore The sum score to be set.
     */
    public void setSumScore(int sumScore) {
        this.sumScore = sumScore;
    }

    /**
     * Sets the name of the player.
     *
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.playerName = name;
    }

    /**
     * Sets the hand size of the player.
     *
     * @param handSize The hand size to be set.
     */
    public void setHandSize(int handSize) {
        this.handSize = handSize;
    }

    /**
     * Sets the player's hand.
     *
     * @param playerHand The player's hand to be set.
     */
    public void setPlayerHand(List<Tile> playerHand) {
        this.playerHand = playerHand;
    }

    // Getters

    /**
     * Returns the hand size of the player.
     *
     * @return The hand size of the player.
     */
    public int getHandSize() {
        return handSize;
    }

    /**
     * Returns the player's hand.
     *
     * @return The player's hand.
     */
    public List<Tile> getPlayerHand() {
        return this.playerHand;
    }

    /**
     * Returns the ID of the player.
     *
     * @return The ID of the player.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the sum score of the player.
     *
     * @return The sum score of the player.
     */
    public int getSumScore() {
        return sumScore;
    }

    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Checks if the player's hand is full (size 7).
     *
     * @return true if the hand is full, false otherwise.
     */
    public boolean handIsFull() {
        return playerHand.size() == 7;
    }

    /**
     * Converts the tiles in the player's hand to a list of strings.
     *
     * @param tiles The tiles to be converted.
     * @return The list of strings representing the tiles.
     */
    public List<String> convertTilesToStrings(List<Tile> tiles) {
        List<String> tmpList = new ArrayList<>();
        for (Tile t : tiles) {
            tmpList.add(String.valueOf(t.getLetter()));
        }
        return tmpList;
    }

    /**
     * Converts a string representation of tiles to a list of Tile objects.
     *
     * @param str The string representation of tiles.
     * @return The list of Tile objects.
     */
    public List<Tile> StringToTiles(String str) {
        List<Tile> tiles = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            if (ch != ',') {
                tiles.add(Tile.Bag.getBag().getTile(ch));
            }
        }
        return tiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && handSize == player.handSize && sumScore == player.sumScore && isTurnOver == player.isTurnOver && Objects.equals(playerName, player.playerName) && Objects.equals(playerHand, player.playerHand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, id, playerHand, handSize, sumScore, isTurnOver);
    }
}
