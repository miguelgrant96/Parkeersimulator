package Views;

import Controllers.SimulatorController;
import Controllers.BetaalAutomaatController;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by Jop on 26-1-2017.
 */
public class PaymentView extends AbstractView {
    private BetaalAutomaatController betaalAutomaatController;
    private JLabel dagOmzetLabel, verwachteDagOmzetLabel, weekOmzetLabel;

    public PaymentView(SimulatorController simulatorController) {

        betaalAutomaatController = new BetaalAutomaatController(simulatorController.getCarController());

        setSize(250, 50);
        setLayout(new GridLayout(0,1));

        dagOmzetLabel = new JLabel("dagomzet: ");
        verwachteDagOmzetLabel = new JLabel("verwachteDagOmzet: ");
        weekOmzetLabel = new JLabel("weekOmzet: ");

        add(dagOmzetLabel);
        add(verwachteDagOmzetLabel);
        add(weekOmzetLabel);

        setVisible(true);

    }


    // Afronden op 2 decimalen

    public void updateView() {
        double dagOmzet = betaalAutomaatController.getDagOmzet();
        double verwachteDagOmzet = betaalAutomaatController.getVerwachteDagOmzet();
        double weekOmzet = betaalAutomaatController.getWeekOmzet();

        DecimalFormat df = new DecimalFormat("####0.00");

        String dagOmzetS = df.format(dagOmzet);
        String verwachteDagOmzetS = df.format(verwachteDagOmzet);
        String weekOmzetS = df.format(weekOmzet);


        dagOmzetLabel.setText("dagomzet: "+dagOmzetS); //Hier de nieuwe data invullen
        verwachteDagOmzetLabel.setText("verwachte omzet: "+verwachteDagOmzetS);
        weekOmzetLabel.setText("weekomzet: "+weekOmzetS);

    }


}
