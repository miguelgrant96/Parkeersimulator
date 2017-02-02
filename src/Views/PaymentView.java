package Views;

import Controllers.BetaalAutomaatController;
import Controllers.TimeController;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by Jop on 26-1-2017.
 */
public class PaymentView extends AbstractView {
    private BetaalAutomaatController betaalAutomaatController;
    private TimeController timeController;
    private JLabel dagOmzetLabel, verwachteDagOmzetLabel, weekOmzetLabel;

    public PaymentView() {

        betaalAutomaatController = (BetaalAutomaatController) super.registeryController.getObjectInstance("Controllers.BetaalAutomaatController");

        //Connecting to the "Main" TimeController
        timeController =(TimeController) super.registeryController.getObjectInstance("Controllers.TimeController");

        setSize(250, 50);
        setLayout(new GridLayout(0,1));

        //Creating labels to show the profits
        dagOmzetLabel = new JLabel("dagomzet: ");
        verwachteDagOmzetLabel = new JLabel("verwachteDagOmzet: ");
        weekOmzetLabel = new JLabel("weekOmzet: ");

        add(dagOmzetLabel);
        add(verwachteDagOmzetLabel);
        add(weekOmzetLabel);

        setVisible(true);

    }

    /**
     * updating the profit values and setting them to the correct decimal points
     */
    public void updateView() {

        //Creating "local" variables to get the current profit
        double dagOmzet = betaalAutomaatController.getDagOmzet();
        double verwachteDagOmzet = betaalAutomaatController.getVerwachteDagOmzet();
        double weekOmzet = betaalAutomaatController.getWeekOmzet();

        //Setting the Double to a 2 decimal format
        DecimalFormat df = new DecimalFormat("####0.00");

        String dagOmzetS = df.format(dagOmzet);
        String verwachteDagOmzetS = df.format(verwachteDagOmzet);
        String weekOmzetS = df.format(weekOmzet);

        //Adding the newly formatted doubles to the "Update" screen
        dagOmzetLabel.setText("dagomzet: "+dagOmzetS);
        verwachteDagOmzetLabel.setText("verwachte omzet: "+verwachteDagOmzetS);
        weekOmzetLabel.setText("weekomzet: "+weekOmzetS);

        //Setting a resetter for Week and day profits
        betaalAutomaatController.doStuff();


    }


}
