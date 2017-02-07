package Views;

import Controllers.TimeController;

import javax.swing.*;
import java.awt.*;

/**
 * Shows date/time in de GUI
 *
 * @author Jop Bakker
 * @version 1
 * @since 27-01-2016
 */
public class TimeView extends AbstractView {

    private TimeController timeController;
    private JLabel month,week,day,time,emptyLabel, emptyLabel2;
    private JPanel Time;

    /**
     * Creating the date/time panel
     */
    public TimeView(){
        // Connecting with the "main" TimeController
        timeController = (TimeController) super.registeryController.getObjectInstance("TimeController");

        // Getting the current Day, Week and Month
        String dayString = "Day: "+timeController.getDay();
        String weekString = "Week: "+timeController.getWeek();
        String monthString = "Month: "+timeController.getMonth();


        setSize(250, 50);
        setLayout(new GridLayout(0,3));

        //Creating the Time panel and the date/time JLabels
        Time = new JPanel(new GridLayout(0,4));
        month = new JLabel(monthString);
        week = new JLabel(weekString);
        day = new JLabel(dayString);
        time = new JLabel(timeController.getTime());

        //Adding the date/time labels to the Time panel
        Time.add(month);
        Time.add(week);
        Time.add(day);
        Time.add(time);

        //Adding an empty label in order to get the DayTime panel in the center of the screen
        emptyLabel = new JLabel();
        emptyLabel2 = new JLabel();

        //Adding the empty label and the Time Panel
        add(emptyLabel);
        add(Time);
        add(emptyLabel2);

        setVisible(true);
    }

    /**
     * Updating the date/time view
     */
    public void updateView(){
        //Updating the date/time with evert tick()
        day.setText("Day: " + timeController.getDay());
        week.setText("Week: " + timeController.getWeek());
        month.setText("Month: " + timeController.getMonth());
        time.setText("Hour: " + timeController.getTime());
    }

    public void setVisibility(boolean visibility)
    {
        setVisible(visibility);
    }
}
