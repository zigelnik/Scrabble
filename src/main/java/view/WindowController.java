package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import view_model.ViewModel;

import java.util.Observable;

public class WindowController extends Observable {

    // mission: paint johnny's nipple
    @FXML
    Canvas joystick;
    @FXML
    Slider throttle,rudder;
    ViewModel vm;
    DoubleProperty aileron, elevators;
    double jx,jy,mx,my;
    boolean mousePushed;
    public double thr_val;


    public WindowController()
    {
        mousePushed=false;
        jx=0;jy=0;
        aileron = new SimpleDoubleProperty();
        elevators = new SimpleDoubleProperty();

    }
    void paint()
    {
        GraphicsContext gc = joystick.getGraphicsContext2D();
        mx  = joystick.getWidth()/2;
        my = joystick.getHeight()/2;
        gc.setFill(Color.PINK);//text color
        gc.setStroke(Color.DEEPPINK);//text color
        gc.fill();
        gc.stroke();
        gc.clearRect(0,0,joystick.getWidth(),joystick.getHeight());
        gc.strokeOval(jx-50,jy-50,100,100);
        gc.fillOval(jx-50,jy-50,100,100);

        aileron.set((jx-mx)/mx);
        elevators.set((my-jy)/my);
    }

    public void mouseDown(MouseEvent me)
    {
        if(!mousePushed)
        {
            mousePushed= true;
            System.out.println("mouse is down");
        }
    }

    public void mouseUp(MouseEvent me)
    {
        if(mousePushed)
        {
            mousePushed= false;
            System.out.println("mouse is up");
            jx = mx;
            jy = my;
            paint();
        }
    }

    public void mouseMove(MouseEvent me)
    {
        if(mousePushed)
        {
            //mousePushed= true;
            System.out.println("mouse move "+me.getX()+","+me.getY());
            jx = me.getX();
            jy = me.getY();
            paint();
        }
    }

    public void init(ViewModel vm) {
        this.vm=vm;
        vm.throttle.bind(throttle.valueProperty());
        vm.rudder.bind(rudder.valueProperty());
        vm.aileron.bind(aileron);
        vm.elevators.bind(elevators);
    }
}