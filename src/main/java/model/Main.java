package model;

public class Main
{

    public static void main(String[] args) {
        Model m = new Model();
        if(args[0].equals("host"))
        {
            Thread hostThread = new Thread(() -> {
                m.hostGame(Integer.parseInt(args[1]));
            });
            hostThread.start();

            m.initGame();
        }
        else if(args[0].equals("join"))
        {
            m.joinGame(args[1],Integer.parseInt(args[2]));
        }


    }
}