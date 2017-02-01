import javax.swing.*;
import Controllers.*;
import Views.*;
import java.awt.*;

public class BuildGUI extends JFrame {

    private SimulatorController simulatorController;

    //Importing all needed views to create the GUI
    private SimulatorView simulatorView;
    private PieView pieView;
    private ButtonView buttons;
    private PaymentView paymentView;
    private TimeView timeView;
    private ReservationView reservationView;


    public BuildGUI() {

        RegisteryController reg = RegisteryController.getInstance();
        reg.addObjectReference(new CarController(3, 6, 30));
        reg.addObjectReference(new TimeController());
        reg.addObjectReference(new CarQueueController());
        reg.addObjectReference(new BetaalAutomaatController());

        simulatorController = new SimulatorController();
        reg.addObjectReference(simulatorController);



        //Initiating all views and creating the "Main" TimeController
        simulatorView = new SimulatorView();
        pieView = new PieView();
        buttons = new ButtonView();
        paymentView = new PaymentView();
        timeView = new TimeView();

        //Setting title of the program
        setTitle("Parkeergarage");
        setLayout(new BorderLayout());

        //Creating panel for the Pieview
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());

        pane.add(pieView, BorderLayout.NORTH);

        //Adding all views to the GUI
        getContentPane().add(timeView, BorderLayout.NORTH);
        getContentPane().add(simulatorView, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.WEST);
        getContentPane().add(paymentView, BorderLayout.PAGE_END);

        getContentPane().add(pane, BorderLayout.EAST);

        pack();

        setVisible(true);
        setResizable(false);

        simulatorController.updateViews();

    }

    /**
     * The main function to start the application
     *
     * @param args
     */
    public static void main(String[] args) {
        new BuildGUI();
    }
}