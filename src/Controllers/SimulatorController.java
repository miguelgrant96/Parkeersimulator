package Controllers;

import Models.*;
import Views.AbstractView;


public class SimulatorController extends AbstractController {



    private TimeController timeController;
    private CarQueueController carQueueController;
    private CarController carController;
    private GarageStats stats;

    // private int stepNumber;
    private boolean running;
    private int tickPause = 100;

    public SimulatorController( ) {
        super();
        carController = (CarController) super.registeryController.getObjectInstance("Controllers.CarController");
        timeController = (TimeController) super.registeryController.getObjectInstance("Controllers.TimeController");
        carQueueController = (CarQueueController) super.registeryController.getObjectInstance("Controllers.CarQueueController");
        stats = (GarageStats) super.registeryController.getObjectInstance("Models.GarageStats");
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