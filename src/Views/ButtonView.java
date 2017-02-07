package Views;

import Controllers.SimulatorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Shows the buttons in the GUI
 *
 * @author Jop Bakker
 * @version 1.5
 * @since 25-01-2016
 */
public class ButtonView  extends AbstractView{

    SimulatorController simController;
    private Timer guiRunTimer;
    private JButton start, stop, add1Hour, add24Hours;


    public ButtonView(){

        //Connecting to the "Main" TimeController
        this.simController = (SimulatorController) super.registeryController.getObjectInstance("SimulatorController");

        // Setting the size of the butoon panel
        setSize(250, 50);
        setLayout(new GridLayout(4,1));

        //Calling the methods for creating the buttons
        startButton();
        stopButton();
        add1HourButton();
        fastforward24hoursButton();

        //Adding the buttons to the panel
        add(start);
        add(stop);
        add(add1Hour);
        add(add24Hours);
        setVisible(true);
    }

    /**
     * Method to create the stop button and its ActionListener
     */
    private void stopButton(){
        stop = new JButton("stop");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(simController.isRunning()) {
                    simController.stoprunning();
                }
            }
        });
    }

    /**
     * Method to create the start button and its ActionListener
     */
    private void startButton() {
        start = new JButton("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simController.startRunning();
                guiRunTimer = new Timer(15, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(simController.isRunning())
                            simController.tick();
                    }
                });
                guiRunTimer.setRepeats(true);
                guiRunTimer.start();
            }
        });
    }

    /**
     * Method to create the add 1 hour and its ActionListener
     */
    private void add1HourButton(){
        add1Hour = new JButton("add 1 hour");
        add1Hour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simController.fastforward(60);

            }
        });
    }

    /**
     * Method to create the add 24 hours and its ActionListener
     */
    private void fastforward24hoursButton(){
        add24Hours = new JButton("add 24 hours");
        add24Hours.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                simController.fastforward(1440);
            }

        });
    }

    /**
     * Method to update data when called
     */
    public void updateView()
    {
        repaint();
    }

    public void setVisibility(boolean visibility)
    {
        setVisible(visibility);
    }
}

