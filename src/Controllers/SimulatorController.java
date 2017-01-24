package Controllers;


import Models.Garage;
import Models.Simulator;
import Views.SimulatorView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;

public class SimulatorController extends AbstractController implements ActionListener{

    private Timer guiRunTimer, guiAddTimer;
    private int guiAddCounter = 100;
    private JButton start, stop, add1, add100;

    public SimulatorController(Simulator simulator) {
        super(simulator);
        setSize(250, 50);
        setLayout(new GridLayout(0,1));
        start = new JButton("start");
        stop = new JButton("stop");
        add1 = new JButton("add1");
        add100 = new JButton("add100");
        add(start);
        add(stop);
        add(add1);
        add(add100);
        setVisible(true);
        //buttons();
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==start){
            simulator.run();
        }
        if(e.getSource()==add1){
            simulator.add1();
        }
        if(e.getSource()==add100){
            simulator.add1();
        }
        if(e.getSource()==stop){
            simulator.stoprunning();
        }
    }
}
 /*
    public void buttons() {



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

        add1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator.add1();
            }
        });
        // Dit zooitje is momenteel alleen om het werkend te hebben ^^
        add100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                guiAddTimer = new Timer(15, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        simulator.tick();
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

*/



