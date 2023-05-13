package model;

public class Main
{

    public static void main(String[] args) {
        ScrabbleFacade scrabbleFacade = new ScrabbleFacade();
        if(args[0].equals("host"))
        {
            scrabbleFacade.hostGame(Integer.parseInt(args[1]));

        }
        else if(args[0].equals("join"))
        {
            scrabbleFacade.joinGame(args[1],Integer.parseInt(args[2]));
        }
    }
}
