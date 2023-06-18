package model.concrete;

import javafx.application.Platform;
import model.Host;
import model.Model;
import model.logic.DictionaryManager;
import model.network.BookScrabbleHandler;
import model.network.GameClientHandler;
import model.network.GameServer;
import model.network.QueryServer;
import view.GamePage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

import static model.network.GameServer.clients;

public class GameState{
     public Tile.Bag bag;
    public List<Player> playersList =  Collections.synchronizedList(new ArrayList<>());
    Map<Integer,List<Tile>> playersHandMap = Collections.synchronizedMap(new HashMap<>());
     public Board board;
     private boolean isGameOver;
    private  static class GameStateHolder{ public static final GameState gm = new GameState();}
    public static GameState getGM() {return GameStateHolder.gm;}
    final Object lock = new Object();
    GamePage gp = GamePage.getGP();
    Model model;
    String msg;



    //CTOR
    public  GameState() {
      board = Board.getBoard();
       bag = Tile.Bag.getBag();
       model = Model.getModel();
      isGameOver = false;
      this.msg = null;
    }

    //Getters
    public Board getBoard()
    {
        return this.board;
    }
    public Tile.Bag getBag()
    {
        return this.bag;
    }
    public  List<Player> getPlayersList() {
        return playersList;
    }

    public  boolean getIsGameOver(){return isGameOver;}


    // Functions
    public  void setTurns(){
        //extracting randomly tile for each player, setting is id, returning to bag
        int id = 1;

        for(Player p : playersList){
            Tile tempTile = bag.getRand();
            p.id = tempTile.score;
            bag.put(tempTile);
        }
        // sorting the list from big id to small id with sorting & reversing the order
        playersList = playersList.stream().sorted(Comparator.comparingInt(Player::getId).reversed())
                .collect(Collectors.toList());

        for(Player p : playersList)
        {
            p.id = id;
            id++;
        }
        //first player at list is now playing first randomly
    }

    public void initHands(){
        for(int i = 0; i < playersList.size(); i++){
            Player tmpPlayer = playersList.get(i);

            for(int j=0;j<playersList.get(i).handSize;j++) {
                tmpPlayer.playerHand.add(bag.getRand());
            }
            playersHandMap.put(tmpPlayer.getId(),tmpPlayer.playerHand);
            String result = String.join(",", tmpPlayer.convertTilesToStrings(tmpPlayer.playerHand));

            for(GameClientHandler client: clients)
            {
                if(client.player.equals(tmpPlayer))
                {
                  client.sendMessage("/init\n"+result);
                }
            }
            //Sending to update the Init pack for each player, using Player method to convert tiles to strings
            if(tmpPlayer.getClass().equals(Host.class)){
                Model.getModel().updatePlayerValues(0,tmpPlayer.convertTilesToStrings(tmpPlayer.playerHand),tmpPlayer.id);
            }
        }
    }
    public  void addPlayer(Player player)
    {
        playersList.add(player);
    }

    public  Player isWinner(){
        int max = 0;
        Player tmpPlayer = null;
        //Winner: when the tiles bag is empty and the winner finished his pack
        if(bag.getTilesCounter() == 0){
            for(Player p : playersList){
                if(max < p.sumScore && p.handSize == 0){
                    max = p.sumScore;
                    tmpPlayer =  p;
                }
            }
            isGameOver = true;
            return tmpPlayer;
        }
        return null;
    }

    public String getTextFiles(){
        String folderPath = "src\\main\\resources\\search_folder";
        StringBuilder textFilesBuilder = new StringBuilder();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if(files != null){
            for(File file: files){
                textFilesBuilder.append(file.getName());
                textFilesBuilder.append(',');
            }
        }
        return textFilesBuilder.toString();
    }

    // converting string to Tiles[] for creating new Word
    public Word convertStrToWord(String strQuery,Player p){
        if(strQuery.equals("")){System.out.println("msg is null!!");return null;}
        //EXAMPLE: "CAR,5,6,False"
        String[] res = strQuery.split(",");
        String word = res[0];
        int row = Integer.parseInt(res[1]);
        int col = Integer.parseInt(res[2]);
        boolean vert = Boolean.parseBoolean(res[3]);

        //after parsing the strings , creating new Word
        Tile[] wordTile = getTileArr(word.toUpperCase(),p);
        Word tmpQuery = new Word(wordTile, row, col, vert);
        System.out.println("after convert str to word");
        return tmpQuery;
    }

    public  Tile[] getTileArr(String str, Player p) {
        Tile[] tileArr =new Tile[str.length()];
        int i=0;
        for(char ch : str.toCharArray())
        {
            for(Tile t: playersHandMap.get(p.getId()))
            {
                if(t.getLetter() == ch)
                {
                    tileArr[i] = t;
                    i++;
                    break;
                }
            }
        }
        return tileArr;
    }


    public void initPlayers()
    {
        setTurns();
        initHands();
    }
    public void initGame(){

        int currPlayerInd = 1;

        while(!getIsGameOver())
        {
            for(Player player : playersList)
            {
                //Holding all the Threads that not their turn!

                while(!player.isTurnOver)
                {
                    player.isTurnOver =  legalMove(player);
                }

                player.isTurnOver = false; // returning so next round the player can play again his turn.
                currPlayerInd = ((currPlayerInd+1) % playersList.size());

                if(player.getClass().equals(Host.class)){
                    model.updatePlayerValues(player.getSumScore(), player.convertTilesToStrings(player.playerHand),player.id); // updating PlayerHand and Score
                    Platform.runLater(()->{
                        gp.updateBoard(msg);
                    });
                }
                else{
                    String result = String.join(",", player.convertTilesToStrings(player.playerHand));
                    for(GameClientHandler client: clients)
                    {
                        if(client.player.equals(player))
                        {
                            client.sendMessage("/update\n" + result + "\n" + player.sumScore + "\n" + msg);
                        }
                    }
                }
                // do we need to get the winner as object or change the isWinner to void?
                Player winner = isWinner();

            }
            //TODO: fix the main bug when using multiple clients!
            //TODO:The break and the stop=true comment is preventing infinty loop when testing one player!

        }
//         stop=true;
    }





    public boolean legalMove(Player player)
    {

        int score=0;

        // if the player is the host
        if (player.getClass().equals(Host.class)) {
            System.out.println("Host, enter your query and press Submit: ");
            synchronized (gp.getLockObject()) {
                try {
                    gp.getLockObject().wait(); // Releases the lock and waits until notified
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                msg = model.getPlayerQuery();
                System.out.println("msg from Host is: " + msg);
                score=  makeMove(msg,player);

            }
        }
        else { // if the player is a regular player
            for (GameClientHandler gch : GameServer.getClients()) {
                if (gch.player.equals(player)) {
                    gch.sendMessage("/turn\n");
                    msg = gch.getQuery();
                    while(msg== null)
                    {
                    }
                    score=  makeMove(msg,player);

                }
            }
        }



        player.sumScore += score;
        return score != 0;
    }

    public int makeMove(String msg , Player p)  {
        // if makeMove fails this integer will stay 0.
        int tmpMoveScore = 0;
        String[] args = msg.split(","); // splitting the query by 4 commas <word,ROW,COL,alignment>
        String books = getTextFiles();
        String queryWord = "Q,"+books+args[0];
        boolean validQuery = true;
        Word w = null;
        w = convertStrToWord(msg,p);
        // if tiles are over
        if(p.getHandSize() == 0){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player wants to place a word with not enough tiles
        else if(w.getTiles().length > p.getHandSize()){
            System.out.println("Tiles are over");
            return tmpMoveScore;
        }
        // if the player don't have all the tiles for the word
//        else if(!isContain(w,p)){
//            System.out.println("Not all word tiles are existed");
//            return tmpMoveScore;
//        }

        // after checking all exceptions, check if the word exist in the books
//        validQuery = tmpDictionaryLegal(queryWord);

        if(validQuery) // if validQuery returned true -> the word exists, now we try placing the word on the board
            tmpMoveScore += getBoard().tryPlaceWord(w);

        // after all checks,decline the words size from pack and init pack back to 7.
        if(tmpMoveScore != 0){
            p.setHandSize(p.getHandSize() - w.getTiles().length);
        }
        p.acceptedQuery = msg;
        p.setSumScore(p.getSumScore()+ tmpMoveScore);
        initHandAfterMove(w , p);
        //if tmpMoveScore is 0 then one of the checks is failed
        return tmpMoveScore;
    }

    // there is dictionaryLegal method from patam1 , return always True.
    public boolean tmpDictionaryLegal(String query ){
        //TODO: with given word we will open new thread to dictionaryServer
        //TODO: check with dm if the word is legal , return true or false
        //TODO: closing the tread, this method will run each time a player want to make move

        boolean rightWord = false;
        model.host.queryServer = new QueryServer(9999, new BookScrabbleHandler());
        model.host.queryServer.start();

        try {
            DictionaryManager dm = DictionaryManager.get();

            Socket server = new Socket("localhost", 9999);
            PrintWriter out = new PrintWriter(server.getOutputStream());

            Scanner in = new Scanner(server.getInputStream());

            out.println(query);
            out.flush();
            String res = in.next();
            System.out.println(res);
            if ( res.equals("true")) {
                rightWord = true;
            }
            else
            {
                System.out.println("problem getting the right answer from the server (-10)");
            }
            in.close();
            out.close();
            server.close();
        } catch (IOException e) {
            System.out.println("your code ran into an IOException (-10)");
            e.printStackTrace();

        }
        model.host.queryServer.close();


        return rightWord;
    }


    // func for re-packing the player hand with tiles after placing word on board
    public void initHandAfterMove(Word w, Player p) {
        List<Tile>tmpWordList = Arrays.stream(w.getTiles()).toList();
        p.setPlayerHand(p.getPlayerHand().stream().filter((t)->!tmpWordList.contains(t)).collect(Collectors.toList()));
        while(!p.handIsFull()){
            p.getPlayerHand().add(getBag().getRand());
            p.setHandSize(p.getHandSize()+1);
        }
    }

    public boolean isContain(Word w, Player p) {
        for(Tile t: p.getPlayerHand()){
            if(!(Arrays.stream(w.getTiles()).toList().contains(t)) && t != null){
                return false;
            }
        }
        return true;
    }
}
