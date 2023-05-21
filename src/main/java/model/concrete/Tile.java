package model.concrete;

import java.util.Objects;
import java.util.Random;


public class Tile {

    public final char letter;
    public final int score;

    private Tile(char ltr, int scr)
    {
        this.letter = ltr;
        this.score = scr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    public char getLetter() {
        return letter;
    }

    public int getScore() {
        return score;
    }

    public static class Bag
    {
        private static Bag bag=null;
        private int tilesCounter = 26;
        private final int[] currAmount =            {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        private final int[] maxAmount =              {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        private final int[] scoreArr =               {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
        private final Tile[] tiles = new Tile[26];
        private Bag() {
            int i;
            char x;
            for (i = 0, x = 'A'; i < tiles.length; i++, x++) {
                this.tiles[i] = new Tile(x, scoreArr[i]);
            }
        }

        // get a random tile, if not found check availability of the next letter
        public Tile getRand()
        {
            Random rand = new Random();
            int i=0,j=0, rand_int = rand.nextInt(26);

        while (!isTileExist(rand_int+i))
        {
            i++;
            j++;
            if (i+rand_int > 26) {
                rand_int = 0;
                i=1;
            }
            if (j == 26) // not found any available tile
                return null;
        }
            this.currAmount[rand_int+i]--;
            return tiles[rand_int+i];
        }

        // check if tile exist by checking it amount from the specific index
        public boolean isTileExist(int num)
        {
            if(this.currAmount[num] == 0) {
                tilesCounter--;
                return false; // not found
            }
            return true;
        }

        // search through the tiles to compare the input letter, if found return it
        public Tile getTile(char ltr)
        {
            for (int i = 0; i< tiles.length; i++)
                if(ltr == tiles[i].getLetter() && isTileExist(i))
                {
                    this.currAmount[i]--;
                    return tiles[i];
                }
            return null;
        }

        // run through to find the letter then check if the maximum amount is there
        public void put(Tile tile)
        {
            int tileIndex = tile.getLetter()-'A';
            if(currAmount[tileIndex] >= maxAmount[tileIndex]) {

            }
            else currAmount[tileIndex]++;
        }

        // maybe try to change the method so that it will return the size in O(1)
        // by holding another data member for this
        public int size()
        {
            int sum=0;
            for (int i = 0; i< tiles.length; i++)
                sum += currAmount[i];

            return sum;
        }


        public int[] getQuantities()
        {
            return getBag().currAmount.clone();
        }

        public static Bag getBag()
        {
             if (bag==null)
                 bag = new Bag();

             return bag;
        }

        public int getTilesCounter() {
            return tilesCounter;
        }
    }

}
