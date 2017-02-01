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
    private CarLeavingView carLeavingView;

    public BuildGUI() {

        RegisteryController reg = RegisteryController.getInstance();
        reg.addObjectReference(new CarController(3, 6, 30));
        reg.addObjectReference(new TimeController());
        reg.addObjectReference(new CarQueueController());
        reg.addObjectReference(new BetaalAutomaatController());
        reg.addObjectReference(new ReservationController());

        simulatorController = new SimulatorController();
        reg.addObjectReference(simulatorController);



        //Initiating all views and creating the "Main" TimeController
        simulatorView = new SimulatorView();
        pieView = new PieView();
        buttons = new ButtonView();
        paymentView = new PaymentView();
        timeView = new TimeView();
        carLeavingView = new CarLeavingView();

        //Setting title of the program
        setTitle("Parkeergarage");
        setLayout(new BorderLayout());

        //Creating panel for the Pieview
        JPanel infoPane = new JPanel();
        infoPane.setLayout(new BorderLayout());

        //Adding the information views in one panel
        infoPane.add(pieView, BorderLayout.NORTH);
        infoPane.add(carLeavingView, BorderLayout.CENTER);
        infoPane.add(paymentView, BorderLayout.LINE_END);

        //Adding all views to the GUI
        getContentPane().add(timeView, BorderLayout.NORTH);
        getContentPane().add(simulatorView, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.WEST);
        getContentPane().add(paymentView, BorderLayout.PAGE_END);

        getContentPane().add(infoPane, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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