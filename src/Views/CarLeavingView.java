package Views;

import Controllers.CarQueueController;
import Controllers.TimeController;

import javax.swing.*;
import java.awt.*;

/**
 * Shows information about the entering and leaving cars
 *
 * @author Marnick
 * @version 1.2
 * @since 27-01-2016
 */
public class CarLeavingView extends AbstractView {
    private CarQueueController carQueueController;
    private TimeController timeController;
    private JLabel queueLabel, queueLeftLabel, carsEnteredLabel, carsLeftLabel;


    //Constructor
    public CarLeavingView(){

        carQueueController = (CarQueueController) super.registeryController.getObjectInstance("CarQueueController");
        timeController =(TimeController) super.registeryController.getObjectInstance("TimeController");

        setSize(250,25);
        setLayout(new GridLayout(0,1));

        //Create Labels
        queueLabel = new JLabel("Auto's in de rij: ");
        queueLeftLabel = new JLabel("Auto's weggereden uit de wachtrij: ");
        carsEnteredLabel = new JLabel("Auto's ingereden vandaag: ");
        carsLeftLabel = new JLabel("Auto's uitgereden vandaag: ");

        add(queueLabel);
        add(queueLeftLabel);
        add(carsEnteredLabel);
        add(carsLeftLabel);

        setVisible(true);
    }
    
    /**
     * Shows the number of waiting,left,entered and cars who had to wait to long
     * Will be resetted every day at 23:30
     */
    public void updateView(){
        int queue = carQueueController.getWaitingCars();
        int queueLeft = carQueueController.getLeftCars();
        int carsEntered = carQueueController.getCarsToday();
        int carsLeft = carQueueController.getLeavingCarsToday();

        queueLabel.setText("Auto's in de rij: "+ queue);
        queueLeftLabel.setText("Auto's weggereden uit de wachtrij: "+queueLeft);
        carsEnteredLabel.setText("Auto's ingereden vandaag: "+carsEntered);
        carsLeftLabel.setText("Auto's uitgereden vandaag: " +carsLeft);

        if (timeController.getTime().equals("23:30")) {
            carQueueController.resetCars();
        }

    }
}