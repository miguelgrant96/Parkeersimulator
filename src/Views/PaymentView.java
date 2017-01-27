package Views;

import Controllers.SimulatorController;
import Models.BetaalAutomaat;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by Jop on 26-1-2017.
 */
public class PaymentView extends AbstractView {
    private BetaalAutomaat betaalAutomaat;
    private JLabel dagOmzet, verwachteDagOmzet, weekOmzet;
    private double test;
    private String test1;

    public PaymentView(SimulatorController simulatorController) {
        setSize(250, 50);
        setLayout(new GridLayout(0,1));

        dagOmzet = new JLabel("dagomzet: ");
        verwachteDagOmzet = new JLabel("verwachteDagOmzet: ");
        weekOmzet = new JLabel("weekOmzet: ");

        add(dagOmzet);
        add(verwachteDagOmzet);
        add(weekOmzet);

        setVisible(true);

    }


    // Afronden op 2 decimalen

    public void updateView() {
        DecimalFormat df = new DecimalFormat("####0.00");
        test1 = df.format(test);
        test+=0.1;

        dagOmzet.setText("dagomzet: "+test1); //Hier de nieuwe dat invullen
        verwachteDagOmzet.setText("verwachte dagelijkseomzet: ");
        weekOmzet.setText("weekomzet: ");

    }

}
