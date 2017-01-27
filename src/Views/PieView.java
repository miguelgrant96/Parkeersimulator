package Views;

import Controllers.*;
import Models.*;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Bessel on 1/24/2017.
 */
public class PieView extends AbstractView{
    private static final long serialVersionUID = -7891669840482084995L;
    //Dimension size;
    private GarageStats stats;
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
                "<font color=#FF6600>ParkingPassCar</font><br>" +
                "<font color=#FF0000>AdHocCar</font><br>" +
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

        stats = simulatorController.getGarageStats();
        int count = 0;
        int index = 0;
        int[] array = new int[stats.getHashMap().size()];
        int oldDegrees=0;
        String[] klasse = new String[stats.getHashMap().size()];
        double percentage = 0;

        //Put number of actors in an array.
        for(Class<?> key : stats.getHashMap().keySet())
        {
            Counter info = stats.getHashMap().get(key);
            count = count + info.getCount();
            array[index] = info.getCount(); // number of actors
            klasse[index] = info.getName(); // names of the classes to set the colour later
            index++;
        }

        // loop through the array and do action
        for(int i=0;i<array.length;i++)
        {
            double x = array[i];
            percentage = (x / count)*100;

            //als percentage / 2 een restwaarde heeft, percentage +1, zodat de PieView goed uitlijnd
            if((percentage % 2) != 0 && (percentage % 2) <= 0.5)
            {
                percentage += 1;
            }

            double graden = (percentage*3.6);
            // set the colour corresponding to the class name
            if(klasse[i].equals("Models.ParkingPassCar"))
            {
                g.setColor(Color.BLUE);
            }
            if(klasse[i].equals("Models.AdHocCar"))
            {
                g.setColor(Color.RED);
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
