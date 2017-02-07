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

        menuBar.add(options());
        menuBar.add(view());

        return menuBar;
    }

    private JMenu options()
    {
        JMenu menu = new JMenu( "Options");
        JMenuItem settings = settings();
        JMenuItem reservation = reservation();
        JMenuItem reset = reset();

        menu.add(settings);
        menu.add(reservation);
        menu.add(reset);
        return menu;
    }

    private JMenu view()
    {
        JMenu menu = new JMenu("View");

        menu.add(infoPanel());
        menu.add(time());

        return menu;
    }


    private JCheckBoxMenuItem infoPanel()
    {
        JCheckBoxMenuItem checkbox = new JCheckBoxMenuItem("Info panel", true);
        checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractView.updateVisibility(checkbox.getState(), "Views.PieView");
                AbstractView.updateVisibility(checkbox.getState(), "Views.CarLeavingView");
                AbstractView.updateVisibility(checkbox.getState(), "Views.PaymentView");
            }
        });

        return checkbox;
    }

    private JCheckBoxMenuItem time()
    {
        JCheckBoxMenuItem checkbox = new JCheckBoxMenuItem("Time", true);
        checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractView.updateVisibility(checkbox.getState(), "Views.TimeView");
            }
        });

        return checkbox;
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
