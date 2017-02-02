package Views;

import Controllers.TimeController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jop on 27-1-2017.
 */
public class TimeView extends AbstractView {

    private TimeController timeController;
    private JLabel month,week,day,time,empty;
    private JPanel Time;
    private String dayString, weekString, monthString;

    /**
     * Creating the time/date panel
     */
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

    /**
     * Updating the date/time view
     */
    public void updateView(){
        //Updating the Time and Day with every Tick()
        day.setText(this.toString(1));
        week.setText(this.toString(2));
        month.setText(this.toString(3));
        time.setText(timeController.getTime());
    }

    /**
     *
     * @param i the item that is chosen
     * @return return the value of output
     */
    public String toString(int i) {
        String output;
        //Overriding the toString() method to het an updatable Day
        switch (i) {
            case 1:
                output = "Day: " + timeController.getDay();
                break;
            case 2:
                output = "Week: " + timeController.getWeek();
                break;
            case 3:
                output = "Month: " + timeController.getMonth();
                break;
            default:
                output = null;
        }
        return output;
    }
}
