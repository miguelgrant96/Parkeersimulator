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

    private boolean melding;


    //Constructor
    public CarLeavingView(){

        carQueueController = (CarQueueController) super.registeryController.getObjectInstance("CarQueueController");
        timeController =(TimeController) super.registeryController.getObjectInstance("TimeController");

        setSize(250,25);
        setLayout(new GridLayout(0,1));

        //Create Labels
        JLabel title = new JLabel("Wachtrij: ");

        queueLabel = new JLabel("Auto's in de rij: ");
        queueLeftLabel = new JLabel("Auto's weggereden uit de wachtrij: ");
        carsEnteredLabel = new JLabel("Auto's ingereden vandaag: ");
        carsLeftLabel = new JLabel("Auto's uitgereden vandaag: ");

        add(title);
        add(queueLabel);
        add(queueLeftLabel);
        add(carsEnteredLabel);
        add(carsLeftLabel);

        setVisible(true);
    }
    
    /**
     * Shows the number of waiting,left,entered and cars who had to wait to long
     * Will be resetted every day at 23:59
     */
    public void updateView(){
        int queue = carQueueController.getWaitingCars();
        if(queue > 10 && melding){
            JOptionPane.showMessageDialog(this,
                    "Wachtrij is momenteel meer dan 10 auto's.",
                    "OPGELET",
                    JOptionPane.WARNING_MESSAGE);
            melding = false;
        }
        int queueLeft = carQueueController.getLeftCars();
        int carsEntered = carQueueController.getCarsToday();
        int carsLeft = carQueueController.getLeavingCarsToday();

        queueLabel.setText("Auto's in de rij: "+ queue);
        queueLeftLabel.setText("Auto's weggereden uit de wachtrij: "+queueLeft);
        carsEnteredLabel.setText("Auto's ingereden vandaag: "+carsEntered);
        carsLeftLabel.setText("Auto's uitgereden vandaag: " +carsLeft);
        if(queue < 5){
            melding = true;
        }

        if (timeController.getTime().equals("23:50")) {
            carQueueController.resetCars();

        }

    }

    public void setVisibility(boolean visibility)
    {
        setVisible(visibility);
    }
}