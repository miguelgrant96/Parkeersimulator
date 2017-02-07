package Controllers;

import Views.AbstractView;


public class SimulatorController extends AbstractController {



    private TimeController timeController;
    private CarQueueController carQueueController;
    private CarController carController;
    private ReservationController reservationController;
    private Boolean fastForward = false;

    // private int stepNumber;
    private boolean running;
    private int tickPause = 100;

    public SimulatorController( ) {
        super();
        carController = (CarController) super.registeryController.getObjectInstance("CarController");
        timeController = (TimeController) super.registeryController.getObjectInstance("TimeController");
        carQueueController = (CarQueueController) super.registeryController.getObjectInstance("CarQueueController");
        reservationController = (ReservationController) super.registeryController.getObjectInstance("ReservationController");
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

    public void startRunning()
    {
        this.running = true;
    }

    public void tick() {

        timeController.advanceTime();
        reservationController.checkReservations();
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

    public int getTickPause()
    {
        return this.tickPause;
    }

    public void setTickPause(int ticks)
    {
        this.tickPause = ticks;
    }

    public void updateViews(){
        carController.tick();
        AbstractView.notifyViews();
    }


}