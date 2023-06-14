//package model.Test;
//import model.concrete.GameState;
//import model.concrete.HostPlayer;
//import model.concrete.Player;
//import model.concrete.Tile;
//import model.concrete.Word;
//
//// HostPlayer extends PLayer so it tests his methods as well
//public class HostPlayerTest {
//    public static void main(String [] args){
//        HostPlayer host = new HostPlayer(new GameState(),"TalOrTalJonathan");
//
//        //model.Test initHands() method
//        for(int i = 0; i < 3; i++) {
//            host.getGameState().getPlayersList().add(new Player());
//        }
//
//        host.getGameState().initHands();
//        for(Player p : host.getGameState().getPlayersList()){
//            if(p.getHandSize() != 7){
//                System.out.println("Problem with init player hand");
//            }
//        }
//        System.out.println("initHands() works successfully");
//
//
//        //model.Test initHandAfterMove() method
//        Word w1 = new Word(host.getGameState().getTileArr("A"), 3, 4, false);
//        Player p = host.getGameState().getPlayersList().get(0);
//        host.initHandAfterMove(w1,p);
//
//        if(p.getHandSize() != 7){
//            System.out.println("Problem with initHandAfterMove()");
//        }
//        System.out.println("initHandAfterMove() works successfully");
//
//
//        //model.Test isContain() method
//        Word w2 = new Word(host.getGameState().getTileArr("ABCDEFGHIJKLM"), 3, 4, false);
//        if(host.isContain(w2,p)){
//            System.out.println("Problem with isContain()");
//        }
//        System.out.println("isContain() works successfully");
//
//
//        //model.Test convertStrToWord() method
//        Word w3 = host.getGameState().convertStrToWord("CAR,4,5,false");
//        if(!w3.isVertical() && w3.getCol() == 5 && w3.getRow() == 4){
//            for(int i = 0; i < 3; i++){
//                String word = "CAR";
//                if(!(w3.getTiles()[i].getLetter() == word.charAt(i))){
//                    System.out.println("Problem with convertStrToWord()");
//                }
//                else {
//                    System.out.println("convertStrToWord() works successfully");
//                }
//            }
//        }
//        else{
//            System.out.println("Problem with convertStrToWord()");
//        }
//
//        //model.Test getTileArr() method
//        int i = 0;
//        String w = "LEWIS";
//        Tile [] word = host.getGameState().getTileArr(w);
//
//        for(Tile t : word){
//            if(t.getLetter() != w.charAt(i)){
//                System.out.println("Problem with getTileArr()");
//            }
//            i++;
//        }
//        System.out.println("getTileArr() works successfully");
//
//
//
//
//
//
//
//
//
//
//
//    }
//
//}