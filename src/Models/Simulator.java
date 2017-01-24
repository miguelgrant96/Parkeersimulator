package Models;

import Controllers.CarQueueController;
import Controllers.TimeController;
import Views.SimulatorView;

/**
 * Created by Bessel on 1/24/2017.
 */
public class Simulator extends AbstractModel {

    private TimeController timeController;
    private CarQueueController carQueueController;
    private SimulatorView simulatorView;
    private Garage garage;

   // private int stepNumber;
    private boolean running;
    private int tickPause = 100;

    public Simulator(){
        garage = new Garage(3, 6, 30);
        timeController = new TimeController();
        carQueueController = new CarQueueController(this);
        //running = true;

    }

    public Garage getGarage(){
        return garage;
    }

    public TimeController getTijd(){
        return timeController;
    }
    public boolean isRunning(){
        return running;
    }


    public void run() {
        for (int i = 0; i < 10000; i++) {
            tick();
            notifyViews();
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
        notifyViews();
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
        garage.tick();
        // Update the car park view.
        notifyViews();
        simulatorView.updateView();
    }
}
