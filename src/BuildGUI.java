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
<<<<<<< HEAD
=======
    private PaymentView paymentView;
>>>>>>> jop
    private TimeView timeView;
    private AbstractController simController;


    public BuildGUI() {

        simulatorController = new SimulatorController();
        simulatorView = new SimulatorView(simulatorController);
        pieView = new PieView(simulatorController);
        buttons = new ButtonView(simulatorController);
<<<<<<< HEAD
        timeView = new TimeView();
=======
        paymentView = new PaymentView(simulatorController);
        timeView = new TimeView();

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        pane.add(pieView,BorderLayout.NORTH);
>>>>>>> jop

        //Set title
        setTitle("Parkeergarage");
        setLayout(new BorderLayout());

<<<<<<< HEAD
        getContentPane().add(timeView,BorderLayout.NORTH);
        getContentPane().add(simulatorController, BorderLayout.WEST);
=======
        getContentPane().add(timeView, BorderLayout.NORTH);
>>>>>>> jop
        getContentPane().add(simulatorView, BorderLayout.CENTER);
        getContentPane().add(simulatorController, BorderLayout.EAST);
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