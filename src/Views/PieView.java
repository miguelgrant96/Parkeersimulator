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
    private CarController carController;
    private JLabel open, adhoclb, passlb, orange, red, blue;
    private JPanel legend;
    /**
     * Constructor of the PieView view
     */
    public PieView() {
        carController = (CarController) super.registeryController.getObjectInstance("CarController");
        setLayout(new BorderLayout());

        setSize(600,400);
        // we would like the view to be 600px wide and 400px in height

        setPreferredSize(new Dimension(600,400));
        // create a legend with the colors used in the pieview

        legend = new JPanel(new GridLayout(0,2));
        open = new JLabel();
        orange = new JLabel("<html><font color=orange>Orange: </font>Free spaces</html> ");
        adhoclb = new JLabel();
        red = new JLabel("<html><font color=red>Red: </font>Paying cars</html>");
        passlb = new JLabel();
        blue = new JLabel("<html><font color=blue>Blue: </font>Subscription holders</html>");
        JLabel title = new JLabel("Bezetting: ");
        JLabel legenda = new JLabel("legenda: ");
        JLabel label1 = new JLabel("");
        JLabel label2 = new JLabel("");
        legend.add(title);
        legend.add(legenda);
        legend.add(open);
        legend.add(orange);
        legend.add(adhoclb);
        legend.add(red);
        legend.add(passlb);
        legend.add(blue);
        legend.add(label1);

        add(legend,BorderLayout.SOUTH);
        setVisible(true);

    }

    @Override
    /**
     * paintComponent paints the new pieview
     */
    public void paintComponent(Graphics g) {

        g.setColor(new Color(238,238,238));
        g.drawRect(0, 0, 600, 400);
        g.fillRect(0, 0, 600, 400);


        int count = 2;
        int[] array = new int[3];
        int oldDegrees=0;
        String[] klasse = new String[3];
        double percentage = 0;


        Counter pass = carController.getPass();
        Counter adhoc = carController.getAdhoc();

        count = count + pass.getCount() + adhoc.getCount() + carController.getNumberOfOpenSpots();
        array[0] = pass.getCount(); // number of actors
        klasse[0] = pass.getName();
        array[1] = adhoc.getCount(); // number of actors
        klasse[1] = adhoc.getName();// names of the classes to set the colour later
        array[2] = carController.getNumberOfOpenSpots();
        klasse[2] = "open";

        // loop through the array and do action
        for(int i=0;i<array.length;i++)
        {
            double x = array[i];
            percentage = (x / count * 100) +1;



            //als percentage / 2 een restwaarde heeft, percentage +1, zodat de PieView goed uitlijnd
            if((percentage % 2) != 0 && (percentage % 2) <= 0.5)
            {
                percentage +=1;
            }

            double graden = (percentage*3.6);
            // set the colour corresponding to the class name
            if(klasse[i].equals("pass"))
            {
                g.setColor(Color.BLUE);
                passlb.setText(klasse[0] + ": " + array[0] + " | " + Math.round(percentage) + "%");

            }
            if(klasse[i].equals("adhoc"))
            {
                g.setColor(Color.RED);
                adhoclb.setText(klasse[1] + ": " + array[1] + " | " + Math.round(percentage) + "%");

            }
            if(klasse[i].equals("open"))
            {
                g.setColor(Color.orange);
                open.setText(klasse[2] + ": " + array[2] + " | " + Math.round(percentage) + "%" );

            }

            // if the degrees is lower than 1, then make it 1 or else it won't show
            if(graden < 1 && graden > 0) graden=1;
            g.fillArc(10,10,200,200,oldDegrees,(int)graden);
            oldDegrees += graden; // keep track of the current position


        }


    }

    public void setVisibility(boolean visibility)
    {
        setVisible(visibility);
    }

    /**
     * update the view (calls the above function)
     */
    public void updateView()
    {
        repaint();
    }
}