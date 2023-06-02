package view;

import view_model.ViewModel;

import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    LandingPage landingPage;
    WaitingPage waitingPage;
    GamePage gamePage;
    ViewModel vm;

    public View(ViewModel vm) {
        this.vm = vm;
        this.landingPage  = new LandingPage();
        this.waitingPage = new WaitingPage();
        this.gamePage = new GamePage();
    }

    public  void host()
    {
        Thread hostThread = new Thread(()-> {
            vm.getModel().hostGame( Integer.parseInt(landingPage.getHostPort()));
        });

    }

    public void join()
    {
        Thread joinThread = new Thread(()-> {
            vm.getModel().joinGame("localhost", 9996);
        });

    }

    @Override
    public void update(Observable o, Object arg) {

    }


    //GETTES
    public LandingPage getLandingPage() {return landingPage;}
    public WaitingPage getWaitingPage() {return waitingPage;}
    public GamePage getGamePage() {return gamePage;}




}