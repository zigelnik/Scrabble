package Tests;

import model.Model;
import model.Player;
import model.ScrabbleFacade;
import model.concrete.Tile;
import model.concrete.Word;

import java.util.ArrayList;
import java.util.Arrays;
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
        Player p1 = new Player("A");
        pList.add(p1);
        Player p2 = new Player("B");
        pList.add(p2);
        Player p3 = new Player("C");
        pList.add(p3);
        Player p4 = new Player("D");
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
            p.initPack();
            if(p.getPackSize() != 7){
                System.out.println("Problm with player initial hand (-10) ");
            }
            for(Tile t: p.getPack()){
                if(t == null){
                    System.out.println("Problem with Tile at players hand (-10)");
                }
            }
        }


        for (Player p: pList){
            p.initPack();
            Word w1 = new Word(get("HORN"), 7, 5, false);
            Word w2 = new Word(get("CAR"), 7, 5, false);
            Word w3 = new Word(get("WHEEL"), 7, 5, false);



        }


        //TODO: testing make move of: word is null,packsize is 0,not enough tiles, tiles of word is contain
        //TODO: after placing tiles, is packsize 7?

    }
}
