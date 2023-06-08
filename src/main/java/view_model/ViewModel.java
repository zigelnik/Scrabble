package view_model;

import javafx.beans.property.*;
import model.Model;
import model.network.GameServer;
import view.View;

import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    static Model m = Model.getModel();




    public ViewModel() {

    }
    public static void hostGame(int port,String name)
    {
        m.hostGame(port,name);
    }
    public static void joinGame(String ip, int port, String name) {
        m.joinGame(ip,port,name);
    }

    public static void initPlayersBoard(){
        GameServer.broadcastToClients("/start");
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public Model getModel() {return m;}



    private  static class ViewModelHolder{ public static final ViewModel vm = new ViewModel();}
    public static ViewModel getViewModel() {return ViewModelHolder.vm;}


}
