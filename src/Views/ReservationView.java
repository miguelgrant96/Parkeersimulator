package Views;

import Controllers.*;
import Models.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Arjen on 31-1-2017.
 */
public class ReservationView extends AbstractView{

    private TimeController timeController;

    private JFrame reservationFrame;
    private JTextField f1, f2 , f3;


    public ReservationView()
    {
        this.CreateResView();
    }

    private void CreateResView()
    {
        timeController = (TimeController) super.registeryController.getObjectInstance("TimeController");

        reservationFrame = new JFrame("Add new reservation");
        reservationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel content = CreatePanel();

        reservationFrame.getContentPane().add("Center", content);
        reservationFrame.pack();
        reservationFrame.setResizable(false);
        reservationFrame.setVisible(true);

    }

    private JPanel CreatePanel()
    {
        JPanel content = new JPanel(new GridLayout(4,2));

        JLabel l1 = new JLabel("Day of arival");
        JLabel l2 = new JLabel("Time of arival");
        JLabel l3 = new JLabel("Licenceplate");

        f1 = new JTextField(12);
        f2 = new JTextField("Hours:Minutes",12);
        f3 = new JTextField(12);

        content.add(l1);
        content.add(f1);
        content.add(l2);
        content.add(f2);
        content.add(l3);
        content.add(f3);

        content.add(acceptButton());
        content.add(cancelButton());

        return content;
    }

    private JButton acceptButton(){
        JButton accept = new JButton("Insert reservation");
        ReservationController resCon = (ReservationController) super.registeryController.getObjectInstance("ReservationController");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    int day = Integer.parseInt(f1.getText());
                    String time = f2.getText();
                    Reservation res = new Reservation(day, time);
                    resCon.addReservation(res);
                    AbstractView.notifyViews();
                    reservationFrame.dispose();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(reservationFrame,
                            "Geen geldig dagnummer of aankomsttijd.",
                            "OPGELET",
                            JOptionPane.WARNING_MESSAGE);

                }

            }
        });

        return accept;
    }

    private JButton cancelButton(){
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservationFrame.dispose();
            }
        });

        return cancel;
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
