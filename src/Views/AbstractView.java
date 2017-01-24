package Views;

/**
 * Created by Bessel on 1/24/2017.
 */
import javax.swing.*;
import Models.*;

public abstract class AbstractView extends JPanel {
    private static final long serialVersionUID = 6437976554496769048L;
    protected Simulator simulator;
    /**
     * Constructor of AbstractView
     * @param simulator
     */
    public AbstractView(Simulator simulator) {
        this.simulator=simulator;
        simulator.addView(this);
    }
    /**
     * @return Simulator The model that has to be used to find the correct methods
     */
    public Simulator getModel() {
        return simulator;
    }
    /**
     * Update the View
     */
    public abstract  void updateView();
}
