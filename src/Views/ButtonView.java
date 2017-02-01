package Views;

import Controllers.SimulatorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Arjen on 25-1-2017.
 */
public class ButtonView  extends AbstractView{

    SimulatorController simController;
    private Timer guiRunTimer, guiAddTimer;
    private int guiAddCounter = 100;
    private JButton start, stop, add1, add100, reservation;

    public ButtonView(){

        this.simController = (SimulatorController) super.registeryController.getObjectInstance("SimulatorController");

        setSize(250, 50);
        setLayout(new GridLayout(0,1));

        startButton();
        stopButton();
        add1Button();
        add100Button();
        reservationButton();

        add(start);
        add(stop);
        add(add1);
        add(add100);
        add(reservation);
        setVisible(true);
    }

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

    private void startButton() {
        start = new JButton("start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                guiRunTimer = new Timer(15, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        simController.add1();
                    }
                });
                guiRunTimer.setRepeats(true);
                guiRunTimer.start();
            }
        });
    }

    private void add1Button(){
        add1 = new JButton("add1");
        add1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simController.add1();
            }
        });
    }

    private void add100Button(){
        add100 = new JButton("ad100");
        add100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                guiAddTimer = new Timer(15, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        simController.add1();
                        System.out.println(guiAddCounter);
                        guiAddCounter--;
                        if(guiAddCounter != 0){
                            guiAddTimer.setRepeats(true);
                        }else {
                            guiAddTimer.setRepeats(false);

                            System.out.println("false");
                            guiAddCounter = 100;

                            

                        }
                    }
                });
                guiAddTimer.start();
            }
        });
    }

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

    public void updateView()
    {
        repaint();
    }

}

