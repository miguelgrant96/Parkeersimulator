package Controllers;

import Models.*;
import Views.AbstractView;


public class SimulatorController extends AbstractController {



    private TimeController timeController;
    private CarQueueController carQueueController;
    private CarController carController;
    private GarageStats stats;
    private BetaalAutomaatController betaalAutomaatController;

    // private int stepNumber;
    private boolean running;
    private int tickPause = 100;

    public SimulatorController( ) {
        carController = new CarController(3, 6, 30);
        timeController = new TimeController();
        carQueueController = new CarQueueController(this);
        stats = new GarageStats();
    }

    public CarController getCarController(){
        return carController;
    }

    public GarageStats getGarageStats(){
        return stats;
    }


    public TimeController getTimeController(){
        return timeController;
    }
    public boolean isRunning(){
        return running;
    }

    public void run() {
        running = true;
        while(running){
            tick();
            AbstractView.notifyViews();
        }
    }

    public void stoprunning() {
        this.running = false;
    }

    public void add1() {
        tick();
    }

    private void tick() {

        timeController.advanceTime();
        carQueueController.handleExit();
        AbstractView.notifyViews();
        updateViews();
        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carQueueController.handleEntrance();
    }

    public void updateViews(){
        carController.tick();
        // Update the car park view.
        //simulatorView.updateView();
        AbstractView.notifyViews();
    }


}