package Views;

import Controllers.ProfitTableController;

import javax.swing.*;
import java.awt.*;



/**
 * Created by Jop on 1-2-2017.
 */
public class ProfitTableView extends AbstractView {

    private ProfitTableController profitTableController;


    public ProfitTableView(){
        profitTableController = (ProfitTableController) super.registeryController.getObjectInstance("ProfitTableController");

        setSize(250, 50);
        setLayout(new GridLayout(0,1));

        //Creating the table from the controller
        JTable table = profitTableController.createProfitTable();

        //Adding a scrollpane to the table
        JScrollPane scrollPane = new JScrollPane(table);

        //Adding the scrollpane to the view
        add(scrollPane);

        setVisible(true);
    }

    /**
     * Method to create the Table and its column names
     * @return the table format
     */


    /**
     *
     * Method to update, reset and save data when needed
     */
    public void updateView() {
        profitTableController.updateTable();
    }
}
