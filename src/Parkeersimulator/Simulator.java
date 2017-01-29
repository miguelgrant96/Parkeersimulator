package Parkeersimulator;

import java.util.Random;
import javax.swing.*;
import java.awt.event.*;

public class Simulator extends JFrame {

    private int stepNumber;

    private static final String AD_HOC = "1";
    private static final String PASS = "2";

    // test 123
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private boolean running;
    private Timer guiRunTimer, guiAddTimer;
    private int guiAddCounter = 100;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30);

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
        advanceTime();
        handleExit();
        updateViews();
        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handleEntrance();
    }



    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    private void handleEntrance(){
        carsArriving();
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
       // passCarsEntering(entrancePassQueue);
    }

    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
       // passCarsLeaving();
    }

    private void updateViews(){
        simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();
    }

    private void carsArriving(){
        int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
    }

    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 &&
                carController.getNumberOfOpenSpots() > 0 &&
                i < enterSpeed) {
            Car car = queue.removeCar();
            if (car.getColor() == Color.blue) {
                Location freeLocation = carController.getFirstFreeLocation();
                carController.setCarAt(freeLocation, car);
            } else (
                    Location freeLocation = carController.getFirstPaidLocation();
            carController.setCarAt(freeLocation, car);
            )
            i++;
        }
    }

      /*  private void passCarsEntering(CarQueue passQueue){
        int i=0;
                while (passQueue.carsInQueue() > 0 &&
                        simulatorView.getNumberOfOpenSpots() > 0 &&
                        i < enterSpeed) {
                    Car cars = passQueue.removePassCar();
                    Location freeLocation = simulatorView.getFirstFreeLocation();
                    simulatorView.setCarAt(freeLocation, cars);
                    i++;
                }
            }*/


    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();
        while (car!=null) {
            if (car.getHasToPay()){
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            }
            else {
                carLeavesSpot(car);
            }
            car = simulatorView.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
        int i=0;
        while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
        }
    }

    private void carsLeaving(){
        // Let cars leave.
        int i=0;
        while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
        }
    }

    /*private void passCarsLeaving() {
        int i=0;
        while (exitCarQueue.carsInPassQueue()>0 && i < exitSpeed){
            exitCarQueue.removePassCar();
            i++;
        }
    }*/

    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
        switch(type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    entrancePassQueue.addCar(new ParkingPassCar());
                }
                break;
        }
    }

    private void carLeavesSpot(Car car){
            simulatorView.removeCarAt(car.getLocation());
            exitCarQueue.addCar(car);
        }

}