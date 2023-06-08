package model.concrete;

import java.util.ArrayList;

public class Board {
    private static final int STAR = 7;
    private static final int BOARD_SIZE = 15;
    private static Tile[][] boardTiles = new Tile[BOARD_SIZE][BOARD_SIZE];
    private final char[][] boardMat = new char[BOARD_SIZE][BOARD_SIZE];
    private final char TRIPLE_WORD_SCR = '!';
    private final char DOUBLE_WORD_SCR = '@';
    private final char TRIPLE_LETTER_SCR = '#';
    private final char DOUBLE_LETTER_SCR = '$';
    private final char STAR_SIGN = '*';
    private static boolean firstWord = false;
private static class BoardHolder{
    public static final Board board = new Board();
}
public static Board getBoard(){
    return BoardHolder.board;
}
    private Board() {

        /*                            Board should look like this:

            {{"3W", " ", " ", "2L", " ", " ", " ", "3W", " ", " ", " ", "2L", " ", " ", "3W"},
            {" ", "2W ", " ", " ", " ", "3L", " ", " ", " ", "3L", " ", " ", " ", "2W", " "},
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
            {"3W", " ", " ", "2L", " ", " ", " ", "3W", " ", " ", " ", "2L", " ", " ","3W"},}
        */

        boardMat[STAR][STAR] = STAR_SIGN;

        boardMat[0][0] = TRIPLE_WORD_SCR;
        boardMat[0][7] = TRIPLE_WORD_SCR;
        boardMat[0][14] = TRIPLE_WORD_SCR;
        boardMat[7][0] = TRIPLE_WORD_SCR;
        boardMat[7][14] = TRIPLE_WORD_SCR;
        boardMat[14][0] = TRIPLE_WORD_SCR;
        boardMat[14][7] = TRIPLE_WORD_SCR;
        boardMat[14][14] = TRIPLE_WORD_SCR;

        boardMat[1][1] = DOUBLE_WORD_SCR;
        boardMat[2][2] = DOUBLE_WORD_SCR;
        boardMat[3][3] = DOUBLE_WORD_SCR;
        boardMat[4][4] = DOUBLE_WORD_SCR;
        boardMat[1][13] = DOUBLE_WORD_SCR;
        boardMat[2][12] = DOUBLE_WORD_SCR;
        boardMat[3][11] = DOUBLE_WORD_SCR;
        boardMat[4][10] = DOUBLE_WORD_SCR;
        boardMat[13][1] = DOUBLE_WORD_SCR;
        boardMat[12][2] = DOUBLE_WORD_SCR;
        boardMat[11][3] = DOUBLE_WORD_SCR;
        boardMat[10][4] = DOUBLE_WORD_SCR;
        boardMat[13][13] = DOUBLE_WORD_SCR;
        boardMat[12][12] = DOUBLE_WORD_SCR;
        boardMat[12][12] = DOUBLE_WORD_SCR;
        boardMat[12][12] = DOUBLE_WORD_SCR;

        boardMat[1][5] = TRIPLE_LETTER_SCR;
        boardMat[1][9] = TRIPLE_LETTER_SCR;
        boardMat[5][5] = TRIPLE_LETTER_SCR;
        boardMat[5][9] = TRIPLE_LETTER_SCR;
        boardMat[5][1] = TRIPLE_LETTER_SCR;
        boardMat[5][13] = TRIPLE_LETTER_SCR;
        boardMat[9][5] = TRIPLE_LETTER_SCR;
        boardMat[9][9] = TRIPLE_LETTER_SCR;
        boardMat[9][1] = TRIPLE_LETTER_SCR;
        boardMat[9][13] = TRIPLE_LETTER_SCR;
        boardMat[13][5] = TRIPLE_LETTER_SCR;
        boardMat[13][9] = TRIPLE_LETTER_SCR;

        boardMat[0][3] = DOUBLE_LETTER_SCR;
        boardMat[0][11] = DOUBLE_LETTER_SCR;
        boardMat[2][6] = DOUBLE_LETTER_SCR;
        boardMat[2][8] = DOUBLE_LETTER_SCR;
        boardMat[3][0] = DOUBLE_LETTER_SCR;
        boardMat[3][7] = DOUBLE_LETTER_SCR;
        boardMat[3][14] = DOUBLE_LETTER_SCR;
        boardMat[6][2] = DOUBLE_LETTER_SCR;
        boardMat[6][6] = DOUBLE_LETTER_SCR;
        boardMat[6][8] = DOUBLE_LETTER_SCR;
        boardMat[6][12] = DOUBLE_LETTER_SCR;
        boardMat[7][3] = DOUBLE_LETTER_SCR;
        boardMat[7][11] = DOUBLE_LETTER_SCR;
        boardMat[8][2] = DOUBLE_LETTER_SCR;
        boardMat[8][6] = DOUBLE_LETTER_SCR;
        boardMat[8][8] = DOUBLE_LETTER_SCR;
        boardMat[8][12] = DOUBLE_LETTER_SCR;
        boardMat[11][0] = DOUBLE_LETTER_SCR;
        boardMat[11][7] = DOUBLE_LETTER_SCR;
        boardMat[11][14] = DOUBLE_LETTER_SCR;
        boardMat[12][6] = DOUBLE_LETTER_SCR;
        boardMat[12][8] = DOUBLE_LETTER_SCR;
        boardMat[14][3] = DOUBLE_LETTER_SCR;
        boardMat[14][11] = DOUBLE_LETTER_SCR;

    }



    // get the tiles that are currently on the board
    public Tile[][] getTiles() {
        Tile[][] curTiles = new Tile[15][15];
        Board board = getBoard();

        for (int i = 0; i < board.boardMat.length; i++)
            for (int j = 0; j < board.boardMat.length; j++)
                if (board.boardTiles[i][j] != null)
                    curTiles[i][j] = board.boardTiles[i][j];

        return curTiles;
    }


    // check if the word can fit within the board's borders
    public boolean WordInBoardsRange(Word w) {
        int row = w.getRow(),
                col = w.getCol(),
                wordSize = w.getTiles().length;
        boolean w_is_horizontal = !w.isVertical();

        if (col < 0 || row < 0)
            return false;

        if (w_is_horizontal && col + wordSize > BOARD_SIZE - 1)
            return false;
        else if (w.isVertical() && row + wordSize > BOARD_SIZE - 1)
            return false;


        return true;
    }

    // check if the first word is on the star
    public boolean FirstWordOnStar(Word w) {
        int row = w.getRow(),
                col = w.getCol(),
                wordSize = w.getTiles().length;
        boolean w_is_horizontal = !w.isVertical();

        for (int i = 0; i < wordSize; i++) {
            if (w_is_horizontal && row == STAR && col + wordSize > STAR)
                return true;
            else if (w.isVertical() && col == STAR && row + wordSize > STAR)
                return true;
        }
        System.out.println("Invalid move!! , first word has to cross the star");

        return false;
    }

    // if we need to complete a tile that have a _ and it צמוד/חופך another word, return that original tile
    public boolean WordsAlign(Word w) {
        int row = w.getRow(),
                col = w.getCol(),
                wordSize = w.getTiles().length;
        boolean w_is_horizontal = !w.isVertical();

        for (int i = 0; i < wordSize; i++) {
            if (w_is_horizontal && w.getTiles()[i] == null &&
                    (boardTiles[row - 1][col + i] != null || boardTiles[row + 1][col + i] != null)) {
                w.getTiles()[i] = boardTiles[row][col + i];
                return true;
            } else if (w.isVertical() && w.getTiles()[i] == null &&
                    (boardTiles[row + i][col - 1] != null || boardTiles[row + i][col + 1] != null)) {
                w.getTiles()[i] = boardTiles[row + i][col];
                return true;
            }
        }
        return false;
    }

    // check if there's a tile around the first word's tile
    public boolean checkAroundWord(Word w) {
               int     row = w.getRow(),
                col = w.getCol(),
                wordSize = w.getTiles().length;
        boolean w_is_horizontal = !w.isVertical();

        for (int i = 0; i < wordSize; i++) {
            if (w_is_horizontal)
                  {
                if (i == 0 && col - 1 > 0 && boardTiles[row][col - 1] != null)                             // check up
                    return true;
                if (row - 1 > 0 && boardTiles[row - 1][col + i] != null)                                  // check left
                    return true;
                if (row + 1 < BOARD_SIZE && boardTiles[row + 1][col + i] != null)                        // check right
                    return true;
                if (i == wordSize - 1 && col + 1 < BOARD_SIZE && boardTiles[row + i + 1][col + 1] != null) //check down
                    return true;
                   }

                else if (w.isVertical())
                   {
                if (i == 0 && row - 1 > 0 && boardTiles[row - 1][col] != null)                              // check up
                    return true;
                if (col - 1 > 0 && boardTiles[row + i][col - 1] != null)                                  // check left
                    return true;
                if (col + 1 < BOARD_SIZE && boardTiles[row + i][col + 1] != null)                        // check right
                    return true;
                if (i == wordSize - 1 && row + 1 < BOARD_SIZE && boardTiles[row + 1][col + i + 1] != null) //check down
                    return true;
                  }
        }
        return false;
    }

// check if the word is legal using different situational methods
    public boolean boardLegal(Word w) {



        if (!WordInBoardsRange(w)) {
            System.out.println("Invalid move!! , word not under board limits");
            return false;
        }

        if (firstWord == false){
            return FirstWordOnStar(w);
        }

        if (WordsAlign(w)){
            return true;
        }
        else{
            System.out.println("Invalid move!! , you cant override tiles");
        }


        if (checkAroundWord(w)){
            return true;
        }
        else{
            System.out.println("Invalid move!! , new word must to be close to exist one");
        }

        return false;}

    // check if there are tiles above or below the word, if so try to locate a new created word
    public Word getWordUpToDown(int row, int col, Word w, int t) {

        int tempSize = 0,
                tempCol = 0,
                tempRow = 0,
                i = 0,
                rowUp = 0,
                rowDown = 0;
        Word tempWord = null;
        ArrayList<Tile> tileArr = null;

        // if there's a tile above and/or below, we will create a tile vector from that size
        while (boardTiles[row - 1 - rowUp][col] != null || boardTiles[row + 1 + rowDown][col] != null) {
            if (boardTiles[row - 1 - rowUp][col] != null && boardTiles[row + 1 + rowDown][col] != null) {
                i += 2;
                rowDown++;
                rowUp++;
            } else if (boardTiles[row - 1 - rowUp][col] != null) {
                i++;
                rowUp++;
            } else if (boardTiles[row + 1 + rowDown][col] != null) {
                i++;
                rowDown++;
            }
        }

        tempCol = col;
        tempRow = row - rowUp;
        tempSize = i + 1;
        tileArr = new ArrayList<>(tempSize);

        // if the board in these coordinates isn't null, there's a tile there, add it to the arr
        // if not, add the new word's tile to the arr
        for (i = 0; i < tempSize; i++) {
            if (boardTiles[tempRow + i][col] != null)
                tileArr.add(boardTiles[tempRow + i][tempCol]);
            else
                tileArr.add(w.getTiles()[t]);
        }

        tempWord = new Word(tileArr.toArray(new Tile[tempSize]), tempRow, tempCol, true);
        return tempWord;
    }

    // same like above but from left to right
    public Word getWordLeftToRight(int row, int col, Word w, int t) {
        int tempSize = 0,
                tempCol = 0,
                tempRow = 0, i = 0,
                colLeft = 0,
                colRight = 0;
        Word tempWord = null;
        ArrayList<Tile> tileArr = null;

        while (boardTiles[row][col - 1 - colLeft] != null || boardTiles[row][col + 1 + colRight] != null) {
            if (boardTiles[row][col - 1 - colLeft] != null && boardTiles[row][col + 1 + colRight] != null) {
                i += 2;
                colLeft++;
                colRight++;
            } else if (boardTiles[row][col - 1 - colLeft] != null) {
                i++;
                colLeft++;
            } else if (boardTiles[row][col + 1 + colRight] != null) {
                i++;
                colRight++;
            }
        }

        tempCol = col - colLeft;
        tempRow = row;
        tempSize = i + 1;
        tileArr = new ArrayList<>(tempSize);

        for (i = 0; i < tempSize; i++) {
            if (boardTiles[row][tempCol + i] != null)
                tileArr.add(boardTiles[tempRow][tempCol + i]);
            else
                tileArr.add(w.getTiles()[t]);
        }

        tempWord = new Word(tileArr.toArray(new Tile[tempSize]), tempRow, tempCol, false);
        return tempWord;
    }

    // check for new words created from putting the current word
    public ArrayList<Word> getWords(Word w) {

        int     row = w.getRow(),
                col = w.getCol(),
                wordSize = w.getTiles().length;
        boolean w_is_horizontal = !w.isVertical();
        ArrayList<Word> WordsArray = new ArrayList<>();
        Word tempWord = null;

        WordsArray.add(w);
        for (int i = 0; i < wordSize; i++) {
            if (w_is_horizontal && boardTiles[row][col + i] == null) {
                if (boardTiles[row - 1][col + i] != null || boardTiles[row + 1][col + i] != null) {
                    //  check the board for words that can be created from top to bottom, also send the index
                    //  where the new word is supposed to land (i)
                    tempWord = getWordUpToDown(row, col + i, w, i);
                    if (boardLegal(tempWord))  // if (dictionarylegal(tempWord))
                        WordsArray.add(tempWord);
                }
            } else if (w.isVertical() && boardTiles[row + i][col] == null) {
                if (boardTiles[row + i][col - 1] != null || boardTiles[row + i][col + 1] != null) {
                    tempWord = getWordLeftToRight(row + i, col, w, i);
                    if (boardLegal(tempWord))
                        WordsArray.add(tempWord);
                }
            }

        }

        //  WordsArray = checkOldWords(WordsArray); maybe doesnt need because the board legal check on temp words
        return WordsArray;
    }

    // remove all words that repeat the word's vector (didnt need this method so far)
    public ArrayList<Word> checkOldWords(ArrayList<Word> WordsArr) {
        Board board = getBoard();
        for (int i = 0; i < board.boardTiles.length; i++) {
            for (int j = 0; j < board.boardTiles.length; j++) {
                if (board.boardTiles[i][j] != null)
                    for (int k = 0; k < WordsArr.size(); k++) {
                        if (WordsArr.get(k).equals(board.boardTiles[i][j])) {
                            WordsArr.remove(k);
                        }
                    }
            }
        }
        return WordsArr;
    }

    // get the word's score according to the board's bonuses
    public int getScore(Word w) {
        int i,  k = 0,
          wordScoreWithoutBonus = 0,
                  bonuses = 0,
              tripleWordCounter = 0,
                doubleWordCounter = 0,
                  row = w.getRow(),
                col = w.getCol(),
                wordSize = w.getTiles().length;
        boolean w_is_horizontal = !w.isVertical();

        for (i = 0; i < wordSize; i++)
            wordScoreWithoutBonus += w.getTiles()[i].getScore();


        if (w_is_horizontal) {
            for (i = 0; i < wordSize; i++) {

                switch (boardMat[row][col + k]) {
                    case '!': //triple word
                        tripleWordCounter++;
                        k++;
                        break;
                    case '@': //double wrd
                        doubleWordCounter++;
                        k++;
                        break;
                    case '*': // star
                        if (boardTiles[row][col + k] == null)
                            doubleWordCounter++;
                        k++;
                        break;
                    case '#': //triple ltr
                        bonuses = bonuses + w.getTiles()[k].getScore() * 3;
                        wordScoreWithoutBonus -= w.getTiles()[k].getScore();
                        k++;
                        break;
                    case '$': //double ltr
                        bonuses = bonuses + w.getTiles()[k].getScore() * 2;
                        wordScoreWithoutBonus -= w.getTiles()[k].getScore();
                        k++;
                        break;
                    default:
                        k++;
                }
            }
        } else if (w.isVertical()) {
            for (i = 0; i < wordSize; i++) {
                switch (boardMat[row + k][col]) {
                    case '!': //triple word
                        tripleWordCounter++;
                        k++;
                        break;
                    case '@': //double wrd
                        doubleWordCounter++;
                        k++;
                        break;
                    case '*': // star
                        if (boardTiles[row + k][col] == null)
                            doubleWordCounter++;
                        k++;
                        break;
                    case '#': //triple ltr
                        bonuses = bonuses + w.getTiles()[k].getScore() * 3;
                        wordScoreWithoutBonus -= w.getTiles()[k].getScore();
                        k++;
                        break;
                    case '$': //double ltr
                        bonuses = bonuses + w.getTiles()[k].getScore() * 2;
                        wordScoreWithoutBonus -= w.getTiles()[k].getScore();
                        k++;
                        break;
                    default:
                        k++;
                }
            }
        }

        bonuses += wordScoreWithoutBonus;

        for (i = 0; i < tripleWordCounter; i++)
            bonuses *= 3;

        for (i = 0; i < doubleWordCounter; i++)
            bonuses *= 2;

        return bonuses;
    }

    // try placing a word by checking all the needed criteria, then returning the word's score
    public int tryPlaceWord(Word w) {

        int  sumScore = 0;
        boolean wordFlag = false;
        Word tempWord = null;
        ArrayList<Word> WordsArray = null;
        if (!firstWord && boardLegal(w))
        {
            firstWord = true;
            wordFlag = true;
        }
        else wordFlag = boardLegal(w);

        if (wordFlag) {
            WordsArray = getWords(w);
            for (int i = 0; i < WordsArray.size(); i++)
            {
                tempWord = WordsArray.get(i);
                if (tempWord != null)
                {
                    int tempWordSize = tempWord.getTiles().length,
                            tempCol = tempWord.getCol(),
                            tempRow = tempWord.getRow();
                    boolean tempWord_is_horizontal = !tempWord.isVertical();

                    sumScore += getScore(tempWord);
                    for (int j = 0; j < tempWordSize; j++)
                    {
                        if (tempWord_is_horizontal && boardTiles[tempRow][tempCol + j] == null)
                            boardTiles[tempRow][tempCol + j] = w.getTiles()[j];
                        else if (tempWord.isVertical() && boardTiles[tempRow + j][tempCol] == null)
                            boardTiles[tempRow + j][tempCol] = w.getTiles()[j];
                    }
                }
            }
        }

        return sumScore;
    }

}

