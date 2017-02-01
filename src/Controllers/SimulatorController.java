package Controllers;

import Views.AbstractView;


public class SimulatorController extends AbstractController {



    private TimeController timeController;
    private CarQueueController carQueueController;
    private CarController carController;

    // private int stepNumber;
    private boolean running;
    private int tickPause = 100;

    public SimulatorController( ) {
        super();
        carController = (CarController) super.registeryController.getObjectInstance("CarController");
        timeController = (TimeController) super.registeryController.getObjectInstance("TimeController");
        carQueueController = (CarQueueController) super.registeryController.getObjectInstance("CarQueueController");
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
        AbstractView.notifyViews();
    }


}