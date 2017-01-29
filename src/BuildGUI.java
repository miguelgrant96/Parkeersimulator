import javax.swing.*;
import Controllers.*;
import Views.*;
import Models.*;
import java.awt.*;

public class BuildGUI extends JFrame {

    private SimulatorController simulatorController;
    private SimulatorView simulatorView;
    private PieView pieView;
    private ButtonView buttons;
    private AbstractController simController;


    public BuildGUI() {

        RegisteryController reg = RegisteryController.getInstance();
        reg.addObjectReference(new CarController(3, 6, 30));
        reg.addObjectReference(new TimeController());
        reg.addObjectReference(new CarQueueController());
        reg.addObjectReference(new GarageStats());

        simulatorController = new SimulatorController();
        reg.addObjectReference(simulatorController);


        simulatorView = new SimulatorView();
        pieView = new PieView();
        buttons = new ButtonView();

        //Set title
        setTitle("Parkeergarage");
        setLayout(new BorderLayout());

        getContentPane().add(simulatorController, BorderLayout.WEST);
        getContentPane().add(simulatorView, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.WEST);
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        pane.add(pieView, BorderLayout.NORTH);
        getContentPane().add(pane, BorderLayout.EAST);

        pack();

        setVisible(true);
        setResizable(false);
        simulatorController.run();

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