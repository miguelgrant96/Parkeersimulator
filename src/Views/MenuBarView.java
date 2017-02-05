package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controllers.*;

/**
 * Created by Arjen on 2/5/2017.
 */
public class MenuBarView {

    public JMenuBar CreateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu( "Options");
        JMenuItem settings = settings();
        JMenuItem reservation = reservation();
        JMenuItem reset = reset();

        menu.add(settings);
        menu.add(reservation);
        menu.add(reset);
        menuBar.add(menu);

        return menuBar;
    }

    private JMenuItem settings()
    {
        JMenuItem settings = new JMenuItem("Settings");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsView();
            }
        });
        return settings;
    }

    private JMenuItem reservation()
    {
        JMenuItem reservation = new JMenuItem("Reservation");
        reservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationView();
            }
        });
        return reservation;
    }

    private JMenuItem reset()
    {
        RegisteryController reg = RegisteryController.getInstance();
        TimeController timeCon = (TimeController) reg.getObjectInstance("TimeController");
        CarController carCon = (CarController) reg.getObjectInstance("CarController");
        PaymentController payCon = (PaymentController) reg.getObjectInstance("PaymentController");
        CarQueueController queCon = (CarQueueController) reg.getObjectInstance("CarQueueController");
        ProfitTableController tablecon = (ProfitTableController) reg.getObjectInstance("ProfitTableController");

        JMenuItem reset = new JMenuItem("Reset simulator");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeCon.resetTime();
                carCon.resetCars();
                payCon.resetPayments();
                queCon.resetQueue();
                tablecon.resetTable();
                AbstractView.notifyViews();
            }
        });
        return reset;
    }
}
