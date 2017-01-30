package Views;

import Controllers.SimulatorController;
import Controllers.TimeController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jop on 27-1-2017.
 */
public class TimeView extends AbstractView {

    private TimeController timeController;
    private SimulatorController simulatorController;
    private JLabel day,time,empty;
    private JPanel dayTime;
    private String dayString;

    public TimeView(SimulatorController simulatorController){

        timeController = simulatorController.getTimeController();

        dayString = ""+timeController.getDay();


        setSize(250, 50);
        setLayout(new GridLayout(0,2));

        empty = new JLabel();
        dayTime = new JPanel(new GridLayout(0,1));
        day = new JLabel(dayString);
        time = new JLabel(timeController.getTime());

        dayTime.add(day);
        dayTime.add(time);

        add(empty);
        add(dayTime);

        setVisible(true);

    }

    public void updateView(){
        day.setText(toString());
        time.setText(timeController.getTime());
    }
    @Override
    public String toString(){
        return dayString = "Day: "+timeController.getDay();
    }
}
