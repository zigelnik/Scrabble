package Test;

import model.Model;

        // this class is also testing Client, GameClientHandler and GameServer classes.
public class ModelTest
{

    // to start run the jar file in artifacts/Scrabble.jar from 2 terminals:
    // the first run should host the game "java -jar Scrabble.jar host <port number>
    // the second terminal should run the command: java-jar Scrabble.jar join <ip> <port>
        public static void main(String[] args) {
            Model m = new Model();
            if(args[0].equals("host"))
            {
                m.hostGame(Integer.parseInt(args[1]));

            }
            else if(args[0].equals("join"))
            {
                m.joinGame(args[1],Integer.parseInt(args[2]));
            }



    }
}