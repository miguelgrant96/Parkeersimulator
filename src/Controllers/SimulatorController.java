package Controllers;


import Views.SimulatorView;

import javax.swing.*;
import java.awt.event.*;

public class SimulatorController extends JFrame {

    private int stepNumber;


    private TimeController timeController;
    private CarQueueController carQueueController;
    private SimulatorView simulatorView;

    private boolean running;
    private Timer guiRunTimer, guiAddTimer;
    private int guiAddCounter = 100;

    private int tickPause = 100;



    public SimulatorController() {

        simulatorView = new SimulatorView(3, 6, 30);
        timeController = new TimeController();
        carQueueController = new CarQueueController(simulatorView, timeController);

        buttons();
    }

    public void buttons() {
        JFrame frame = new JFrame();

        JPanel optionPane = new JPanel();

        JButton start = new JButton("start");
        JButton stop = new JButton("stop");
        JButton add1 = new JButton("add1");
        JButton add100 = new JButton("add100");
        JTextArea stepcount = new JTextArea();
        stepcount.setText("Step: "+stepNumber);



        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                guiRunTimer = new Timer(15, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        add1();
                    }
                });
                guiRunTimer.setRepeats(true);
                guiRunTimer.start();
            }
        });


        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(running) {
                    stoprunning();
                }else{
                    guiRunTimer.setRepeats(false);
                }
            }
        });

        add1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add1();
            }
        });
        // Dit zooitje is momenteel alleen om het werkend te hebben ^^
        add100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                guiAddTimer = new Timer(15, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tick();
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

        optionPane.add(start);
        optionPane.add(stop);
        optionPane.add(add1);
        optionPane.add(add100);
        optionPane.add(stepcount);
        frame.add(optionPane);
        frame.pack();
        frame.setVisible(true);
    }


    public void run() {
        running = true;
        while(running){
            tick();
        }
    }

    public void stoprunning() {
        this.running = false;
    }

    public void add1() {
        tick();
    }

    public void tick() {
        stepNumber++;
        timeController.advanceTime();
        carQueueController.handleExit();
        updateViews();
        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carQueueController.handleEntrance();
    }










    private void updateViews(){
        simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();
    }


}