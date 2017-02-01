package Views;

import javax.swing.*;

/**
 * Created by Arjen on 31-1-2017.
 */
public class ReservationView extends AbstractView{

    public ReservationView()
    {
        this.CreateResView();
       // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void CreateResView()
    {
        JDialog dialog = new JDialog();
        setVisible(true);
    }

    public void updateView()
    {
        repaint();
    }
}
