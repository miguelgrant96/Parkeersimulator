package Views;

import Controllers.CarController;
import Controllers.SimulatorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Arjen on 2/5/2017.
 */
public class SettingsView extends AbstractView{

    private CarController carCon;
    private SimulatorController simulatorController;

    private JFrame settingsFrame;
    private JTextField t1, t2;

    public SettingsView()
    {
        carCon = (CarController) super.registeryController.getObjectInstance("CarController");
        simulatorController = (SimulatorController) super.registeryController.getObjectInstance("SimulatorController");

        this.CreateView();

    }

    private void CreateView()
    {
        settingsFrame = new JFrame();
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel content = CreatePanel();

        settingsFrame.getContentPane().add("Center", content);
        settingsFrame.pack();
        settingsFrame.setResizable(false);
        settingsFrame.setVisible(true);
    }

    private JPanel CreatePanel()
    {
        JPanel content = new JPanel(new GridLayout(3,2));

        JLabel l1 = new JLabel("Abbonementen");
        t1 = new JTextField(String.valueOf(carCon.getPassSpots()) ,12);

        JLabel l2 = new JLabel("Snelheid");
        t2 = new JTextField(String.valueOf(simulatorController.getTickPause()+"") ,5);

        content.add(l1);
        content.add(t1);
        content.add(l2);
        content.add(t2);


        content.add(updateButton());
        content.add(cancelButton());

        return content;
    }

    public JButton cancelButton()
    {
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.dispose();
            }
        });

        return cancel;
    }

    public JButton updateButton()
    {

        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    String passSpots = t1.getText();
                    carCon.setPassSpots(Integer.parseInt(passSpots));

                    String speed = t2.getText();
                    simulatorController.setTickPause(Integer.parseInt(speed));

                    AbstractView.notifyViews();
                    settingsFrame.dispose();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(settingsFrame,
                            "Geen geldig nummer.",
                            "OPGELET",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        return update;
    }

    public void updateView()
    {
        repaint();
    }

    public void setVisibility(boolean visibility)
    {
        setVisible(visibility);
    }
}
