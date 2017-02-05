package Views;

import Controllers.CarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Arjen on 2/5/2017.
 */
public class SettingsView extends AbstractView{

    CarController carCon;
    JFrame settingsFrame;
    JTextField t1;

    public SettingsView()
    {
        carCon = (CarController) super.registeryController.getObjectInstance("CarController");
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
        JPanel content = new JPanel(new GridLayout(2,2));

        JLabel l1 = new JLabel("Abbonementen");
        t1 = new JTextField(String.valueOf(carCon.getPassSpots()) ,12);

        content.add(l1);
        content.add(t1);

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

                }
                catch(Exception ex)
                {
                    // je kunt hier een leuke error message teruggeven.
                }
                AbstractView.notifyViews();
                settingsFrame.dispose();
            }
        });

        return update;
    }

    public void updateView()
    {
        repaint();
    }
}