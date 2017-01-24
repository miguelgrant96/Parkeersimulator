package Controllers;

/**
 * Created by Bessel on 1/24/2017.
 */

import javax.swing.*;
// importing project classes
import Models.*;

public abstract class AbstractController extends JPanel
{
    // instance variables
    protected Simulator simulator; // to save the model
    private static final long serialVersionUID = -65786798576L;

    /**
     * Constructor for AbstractController
     * @param Simulator simulator
     */
    public AbstractController(Simulator simulator)
    {
        if(simulator != null)
        {
            this.simulator=simulator;
        }
    }
}