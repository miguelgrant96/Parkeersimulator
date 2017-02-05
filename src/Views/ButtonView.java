package Views;

import Controllers.SimulatorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jop on 25-1-2017.
 */
public class ButtonView  extends AbstractView{

    SimulatorController simController;
    private Timer guiRunTimer, guiAddTimer;
    private int guiAddCounter = 100;
    private JButton start, stop, add1, add100, reservation;

    public ButtonView(){

        //Connecting to the "Main" TimeController
        this.simController = (SimulatorController) super.registeryController.getObjectInstance("SimulatorController");

        // Setting the size of the butoon panel
        setSize(250, 50);
        setLayout(new GridLayout(0,1));

        //Calling the methods for creating the buttons
        startButton();
        stopButton();
        add1Button();
        add100Button();
        reservationButton();

        //Adding the buttons to the panel
        add(start);
        add(stop);
        add(add1);
        add(add100);
        add(reservation);

        //Making the panel visible
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
                }else if(guiAddCounter > 0 && guiAddCounter != 100){
                    guiAddTimer.setRepeats(false);
                    guiAddCounter = 100;
                } else {
                    guiRunTimer.setRepeats(false);
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

                guiRunTimer = new Timer(15, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        simController.tick();
                    }
                });
                guiRunTimer.setRepeats(true);
                guiRunTimer.start();
            }
        });
    }

    /**
     * Method to create the asdd1 step button and its ActionListener
     */
    private void add1Button(){
        add1 = new JButton("add1");
        add1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simController.add1();
            }
        });
    }

    /**
     * Method to create the add100 button and its ActionListener
     */
    private void add100Button(){
        add100 = new JButton("ad100");
        add100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                guiAddTimer = new Timer(15, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        simController.tick();
                        guiAddCounter--;
                        if(guiAddCounter != 0){
                            guiAddTimer.setRepeats(true);
                        }else {
                            guiAddTimer.setRepeats(false);
                            guiAddCounter = 100;
                        }
                    }
                });
                guiAddTimer.start();
            }
        });
    }

    /**
     * Method to create the reservation button and its ActionListener
     */
    private void reservationButton()
    {
        this.reservation = new JButton("Add Reservation");
        reservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationView();
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

}

