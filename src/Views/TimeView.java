package Views;

import Controllers.TimeController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jop on 27-1-2017.
 */
public class TimeView extends AbstractView {

    private TimeController timeController;
    private JLabel time,empty;

    public TimeView(){

        timeController = new TimeController();

        setSize(250, 50);
        setLayout(new GridLayout(0,2));

        empty = new JLabel();
        time = new JLabel(timeController.getTime());

        add(empty);
        add(time);

        setVisible(true);

    }

    public void updateView(){
        timeController.advanceTime();
        System.out.println(timeController.getTime());
        time.setText(timeController.getTime());
    }
}
