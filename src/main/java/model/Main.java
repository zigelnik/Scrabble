package model;

public class Main
{

    public static void main(String[] args) {
        Model m = new Model();


        Thread hostThread = new Thread(() -> {
            m.hostGame(9993);
        });
        hostThread.start();

        m.joinGame("localhost",9993);


    }
}