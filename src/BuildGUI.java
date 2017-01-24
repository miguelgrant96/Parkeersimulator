import javax.swing.*;
import Controllers.*;
import Views.*;
import Models.*;
import java.awt.*;

public class BuildGUI extends JFrame {
    // audio files

    private Simulator simulator;
    private SimulatorView simulatorView;
    private PieView pieView;
    //private SimulatorController simulatorController;
    private AbstractController simController;


    public BuildGUI() {
        simulator = new Simulator();
        simController = new SimulatorController(simulator);
        simulatorView = new SimulatorView(simulator);
        pieView = new PieView(simulator);

        //Set title
        setTitle("Parkeergarage");
        setLayout(new BorderLayout());

        getContentPane().add(simController, BorderLayout.WEST);
        getContentPane().add(simulatorView, BorderLayout.CENTER);
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        pane.add(pieView,BorderLayout.NORTH);
        getContentPane().add(pane, BorderLayout.EAST);

        pack();

        setVisible(true);
        setResizable(false);
        simulator.run();

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