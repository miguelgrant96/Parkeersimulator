package Views;

import Controllers.ProfitTableController;

import javax.swing.*;
import java.awt.*;



/**
 * Shows the table in the GUI
 *
 * @author Jop Bakker
 * @version 1.1
 * @since 1-02-2016
 */
public class ProfitTableView extends AbstractView {

    private ProfitTableController profitTableController;

    private JLabel emptyLabel, emptyLabel2;

    public ProfitTableView(){
        profitTableController = (ProfitTableController) super.registeryController.getObjectInstance("ProfitTableController");

        setSize(250, 50);
        setLayout(new GridLayout(0, 3));
        emptyLabel = new JLabel();
        emptyLabel2 = new JLabel();

        //Creating the table from the controller
        JTable table = profitTableController.createProfitTable();

        //Adding a scrollpane to the table
        JScrollPane scrollPane = new JScrollPane(table);

        //Adding the scrollpane to the view
        add(emptyLabel);
        add(emptyLabel2);
        add(scrollPane);

        setVisible(true);
    }

    /**
     *
     * Method to update, reset and save data when needed
     */
    public void updateView() {
        profitTableController.updateTable();
    }

    public void setVisibility(boolean visibility)
    {
        setVisible(visibility);
    }
}
