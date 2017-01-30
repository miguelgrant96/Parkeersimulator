package Views;

import Controllers.*;
import Models.*;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Bessel on 1/24/2017.
 */
public class PieView extends AbstractView{
    //Dimension size;

    private CarController garage;

    private SimulatorController simulatorController;


    /**
     * Constructor of the PieView view
     * @param simulatorController
     */
    public PieView(SimulatorController simulatorController) {
        this.simulatorController = simulatorController;
        setLayout(new BorderLayout());
        setSize(600,400);
        // we would like the view to be 600px wide and 400px in height
        setPreferredSize(new Dimension(600,400));
        // create a legend with the colors used in the pieview
        JLabel legend = new JLabel("<html>" +
                "<font color=#BLUE>ParkingPassCar: </font><br>" +
                "<font color=#FFGREEN>AdHocCar: </font><br>" +
                "</html>");
        add(legend,BorderLayout.SOUTH);
    }
    @Override
    /**
     * paintComponent paints the new pieview
     */
    public void paintComponent(Graphics g) {

        g.setColor(Color.WHITE);
        g.drawRect(0, 0, 600, 400);
        g.fillRect(0, 0, 600, 400);


        garage = simulatorController.getGarageController();
        int count = 2;

        int index = 0;
        int[] array = new int[2];
        int oldDegrees=0;
        String[] klasse = new String[2];
        double percentage = 0;

        Counter pass = garage.getPass();
        Counter adhoc = garage.getAdhoc();

        count = count + pass.getCount() + adhoc.getCount();
        array[0] = pass.getCount(); // number of actors
        klasse[0] = pass.getName();
        array[1] = adhoc.getCount(); // number of actors
        klasse[1] = adhoc.getName();// names of the classes to set the colour later


        // loop through the array and do action
        for(int i=0;i<array.length;i++)
        {
            double x = array[i];
            percentage = (x / count * 100);

            //als percentage / 2 een restwaarde heeft, percentage +1, zodat de PieView goed uitlijnd
            if((percentage % 2) != 0 && (percentage % 2) <= 0.5)
            {
                percentage += 1;
            }

            double graden = (percentage*3.6);
            // set the colour corresponding to the class name
            if(klasse[i].equals("pass"))
            {
                g.setColor(Color.BLUE);
            }
            if(klasse[i].equals("adhoc"))
            {
                g.setColor(Color.GREEN);
            }

            // if the degrees is lower than 1, then make it 1 or else it won't show
            if(graden<1 && graden > 0) graden=1;
            g.fillArc(10,10,200,200,oldDegrees,(int)graden);
            oldDegrees += graden; // keep track of the current position
        }

    }

    /**
     * update the view (calls the above function)
     */
    public void updateView()
    {
        repaint();
    }
}