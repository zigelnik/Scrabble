package Test;

import model.GameState;
import model.Player;
import model.concrete.Tile;
import model.concrete.Word;
import java.util.ArrayList;
import java.util.List;

public class PlayerTest {

    public static Tile[] get(String s) {
        Tile[] ts=new Tile[s.length()];
        int i=0;
        for(char c: s.toCharArray()) {
            ts[i]= Tile.Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }

    public static void main(String[] args) {
        List<Player> pList = new ArrayList<>();
        Player p1 = new Player();
        pList.add(p1);
        Player p2 = new Player();
        pList.add(p2);
        Player p3 = new Player();
        pList.add(p3);
        Player p4 = new Player();
        pList.add(p4);


        //checking of different ID
        for(int i =0; i < pList.size()-1; i++){
            Player tmpPlayer = pList.get(i);
            if(tmpPlayer.getId() == pList.get(i+1).getId()){
                System.out.println("Same players at the game (-10)");
            }
        }

        // checking initial packsize and tiles
        for(Player p : pList){
        //    p.initHand();
            if(p.getHandSize() != 7){
                System.out.println("Problem with player initial hand (-10) ");
            }
            for(Tile t: p.getPlayerHand()){
                if(t == null){
                    System.out.println("Problem with Tile at players hand (-10)");
                }
            }
        }

        Word w1 = new Word(get("UNICORNS"), 3, 4, false);
        Word w2 = new Word(get("CAR"), 7, 5, false);
        Word w3 = new Word(get("WHEEL"), 7, 5, false);
        for (Player p: pList){
         //   p.initHand();
        }
//        if(p1.makeMove(w1) > 0){
//            System.out.println("Problem with packSize at p1 hand (-10)");
//        }
//        if(p1.getHandSize() != 7 && !GameState.getGameState().getIsGameOver()){
//            System.out.println("Problem with player re-packing while game not over (-10)");
//        }

    }
}