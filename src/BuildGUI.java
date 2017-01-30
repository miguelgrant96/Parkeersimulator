import javax.swing.*;
import Controllers.*;
import Views.*;
import java.awt.*;

public class BuildGUI extends JFrame {

    private SimulatorController simulatorController;
    private SimulatorView simulatorView;
    private PieView pieView;
    private ButtonView buttons;

    private PaymentView paymentView;
    private TimeView timeView;
    private AbstractController simController;


    public BuildGUI() {

        simulatorController = new SimulatorController();
        simulatorView = new SimulatorView(simulatorController);
        pieView = new PieView(simulatorController);
        buttons = new ButtonView(simulatorController);
        paymentView = new PaymentView(simulatorController);
        timeView = new TimeView(simulatorController);

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        pane.add(pieView,BorderLayout.NORTH);

        //Set title
        setTitle("Parkeergarage");
        setLayout(new BorderLayout());


        getContentPane().add(timeView, BorderLayout.NORTH);
        getContentPane().add(simulatorView, BorderLayout.CENTER);
        getContentPane().add(simulatorController, BorderLayout.EAST);
        getContentPane().add(buttons, BorderLayout.WEST);
        getContentPane().add(paymentView, BorderLayout.PAGE_END);
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        pane.add(pieView,BorderLayout.NORTH);
        getContentPane().add(pane, BorderLayout.EAST);

        pack();

        setVisible(true);
        setResizable(false);

        simulatorController.run();


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