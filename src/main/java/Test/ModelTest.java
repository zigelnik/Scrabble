package Test;

import model.Model;

public class ModelTest
{


    public static void main(String[] args) {
        Model m = new Model();


        Thread hostThread = new Thread(() -> {
            m.hostGame(9998);
        });
        hostThread.start();



        Thread joinThread = new Thread(()-> {
            m.joinGame("localhost", 9998);
        });
        joinThread.start();




    }
}