package view;

import model.Model;
import model.network.GameServer;
import view_model.ViewModel;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    LandingPage landingPage;
//    WaitingPage waitingPage;
    GamePage gamePage;
   public static ViewModel vm = ViewModel.getViewModel();


    public View() {
        this.landingPage  = new LandingPage();
//        this.waitingPage = new WaitingPage();
        this.gamePage = new GamePage();
    }

    @Override
    public void update(Observable o, Object arg) {

    }


    //GETTES


    public LandingPage getLandingPage() {return landingPage;}
//    public WaitingPage getWaitingPage() {return waitingPage;}
    public GamePage getGamePage() {return gamePage;}


    private  static class ViewHolder{ public static final View v = new View();}
    public static View getView() {return ViewHolder.v;}



}