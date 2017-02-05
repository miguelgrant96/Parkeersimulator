package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Arjen on 2/5/2017.
 */
public class MenuBarView {

    public JMenuBar CreateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu( "Options");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem reservation = new JMenuItem("Reservation");
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsView();
            }
        });
        reservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationView();
            }
        });

        menu.add(settings);
        menu.add(reservation);
        menuBar.add(menu);

        return menuBar;
    }
}
