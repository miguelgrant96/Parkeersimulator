import Controllers.BetaalAutomaatController;
import Controllers.RegisteryController;
import Controllers.TimeController;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jop on 1-2-2017.
 */



public class ProfitData {
    private RegisteryController registeryController;
    private BetaalAutomaatController betaalAutomaatController;
    private TimeController timeController;

    private double maandOmzet;
    private double jaarOmzet;

    public ProfitData() {
        registeryController = RegisteryController.getInstance();
        betaalAutomaatController = (BetaalAutomaatController) registeryController.getObjectInstance("Controllers.BetaalAutomaatController");
        timeController =(TimeController) registeryController.getObjectInstance("Controllers.TimeController");
    }

    /*public double maandOmzet(){
        maandOmzet =

        return maandOmzet;
    }*/
}
