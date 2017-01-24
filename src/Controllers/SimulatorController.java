package Controllers;

import Models.Simulator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulatorController extends AbstractController {

    private Timer guiRunTimer, guiAddTimer;
    private int guiAddCounter = 100;
    private JButton start, stop, add1, add100;

    public SimulatorController(Simulator simulator) {
        super(simulator);
        setSize(250, 50);
        setLayout(new GridLayout(0,1));

        startButton();
        stopButton();
        add1Button();
        add100Button();

        add(start);
        add(stop);
        add(add1);
        add(add100);
        setVisible(true);
    }
    private void stopButton(){
        stop = new JButton("stop");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(simulator.isRunning()) {
                    simulator.stoprunning();
                }else{
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
                        simulator.add1();
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
                simulator.add1();
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
                        simulator.add1();
                        System.out.println(guiAddCounter);
                        guiAddCounter--;
                        if(guiAddCounter != 0){
                            guiAddTimer.setRepeats(true);
                        }else {
                            guiAddTimer.setRepeats(false);
                            System.out.println("false");
                        }
                    }
                });
                guiAddTimer.start();
            }
        });
    }
}