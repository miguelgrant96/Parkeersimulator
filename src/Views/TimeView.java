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
    private JLabel month,week,day,time,empty;
    private JPanel Time;
    private String dayString, weekString, monthString;

    public TimeView(){
        // Connecting with the "main" TimeController
        timeController = (TimeController) super.registeryController.getObjectInstance("Controllers.TimeController");

        // Getting the current day
        dayString = "Day: "+timeController.getDay();
        weekString = "Week: "+timeController.getWeek();
        monthString = "Month: "+timeController.getMonth();


        setSize(250, 50);
        setLayout(new GridLayout(0,2));


        Time = new JPanel(new GridLayout(0,1));
        month = new JLabel(monthString);
        week = new JLabel(weekString);
        day = new JLabel(dayString);
        time = new JLabel(timeController.getTime());

        Time.add(month);
        Time.add(week);
        Time.add(day);
        Time.add(time);

        //Adding an empty label in order to get the DayTime panel in the center of the screen
        empty = new JLabel();

        add(empty);
        add(Time);

        setVisible(true);

    }

    public void updateView(){
        //Updating the Time and Day with every Tick()
        day.setText(this.toString(1));
        week.setText(this.toString(2));
        month.setText(this.toString(3));
        time.setText(timeController.getTime());
    }

    public String toString(int i) {
        String value = "";
        //Overriding the toString() method to het an updatable Day
        if (i == 1) {
            value = "Day: " + timeController.getDay();
        } else if (i == 2) {
            value = "Week: " + timeController.getWeek();
        } else if (i == 3) {
            value = "Month: " + timeController.getMonth();
        }else {
        }
        return value;
    }
}
