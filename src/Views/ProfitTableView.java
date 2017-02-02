package Views;

import Controllers.PaymentController;

import Controllers.TimeController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;


/**
 * Created by Jop on 1-2-2017.
 */
public class ProfitTableView extends AbstractView {

    private PaymentController paymentController;
    private TimeController timeController;

    private DefaultTableModel model = new DefaultTableModel();

    private int called = 0;



    public ProfitTableView(){
        //Connecting to the "Main" TimeController
        timeController = (TimeController) super.registeryController.getObjectInstance("TimeController");

        setSize(250, 50);
        setLayout(new GridLayout(0,1));
        JTable table = createTable();
        JScrollPane scrollPane = new JScrollPane(table);


        add(scrollPane);

        setVisible(true);
    }

    /**
     * Method to create the Table and its column names
     * @return the table format
     */
    private JTable createTable(){
        model.addColumn("year");
        model.addColumn("Month");
        model.addColumn("Week");
        model.addColumn("Profit");

        JTable profitTable = new JTable(model);
        profitTable.setPreferredScrollableViewportSize(new Dimension(600,150));
        profitTable.setFillsViewportHeight(false);

        model.addRow(new Object[]{timeController.getYear(),getMonthName(timeController.getMonth()), "", ""});
        return profitTable;
    }

    /**
     * Method to add the choses data to the JTable
     * @param i Integer to choose which dataSet is created and thus which row is added
     */
    public void addRow(int i){
        model.addRow(createData(i));
    }

    /**
     *
     * @param i Integer to choose which case is used
     * @return The name of the month corresponding the month number i
     */
    private String getMonthName(int i){
        String monthString;
        switch (i) {
            case 1:  monthString = "January";
                break;
            case 2:  monthString = "February";
                break;
            case 3:  monthString = "March";
                break;
            case 4:  monthString = "April";
                break;
            case 5:  monthString = "May";
                break;
            case 6:  monthString = "June";
                break;
            case 7:  monthString = "July";
                break;
            case 8:  monthString = "August";
                break;
            case 9:  monthString = "September";
                break;
            case 10: monthString = "October";
                break;
            case 11: monthString = "November";
                break;
            case 12: monthString = "December";
                break;
            default: monthString = "Invalid month";
                break;
        }
        return monthString;
    }

    /**
     *
     * @param i Integer to choose which case is used
     * @return Setting the Double to a 2 decimal format
     */
    private String getDecimalProfit(int i){
        DecimalFormat df = new DecimalFormat("####0.00");
        String output;
        switch(i){
            case 1:
                output = df.format(paymentController.getFromWeken(timeController.getWeek()));
                break;
            case 2:
                output = df.format(paymentController.getFromMaanden(timeController.getMonth()));
                break;
            default:
                output = "Profit not found";
        }
        return output;
    }

    private void clearTable(){
        for(int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
        }
    }

    /**
     *
     * @param dataSet Integer to choose which dataSet is created
     * @return the data to be added to the table
     */
    private Object[] createData(int dataSet){
        paymentController = (PaymentController) super.registeryController.getObjectInstance("PaymentController");

        Object[] data = {"","", "", ""};
        switch(dataSet){
            case 1:
                data = new Object[] {timeController.getYear(),getMonthName(timeController.getMonth()+1), "", ""};
                break;

            case 2:
                data = new Object[] {"", "", timeController.getWeek(), getDecimalProfit(1)};
                break;

            case 3:
                data = new Object[] {"", "", "", getDecimalProfit(2)};
                break;
            default:
                data = new Object[] {"","", "", ""};
                break;
        }
        return data;
    }

    /**
     *
     * Method to update or reset data at specified time
     */
    public void updateView(){
        called++;
        String resetTime = "23:30";
        if (called == 2) {
            called = 0;
            if(timeController.getMonth() == 12 && timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals(resetTime)){

                paymentController.clearWeken();
                paymentController.clearMaanden();
                clearTable();
                model.addRow(new Object[]{timeController.getYear(),getMonthName(timeController.getMonth()), "", ""});
            }
            else if (timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals(resetTime)){
                addRow(2);
                addRow(3);
                addRow(0);
                addRow(1);
                paymentController.clearWeken();
            }
            else if (timeController.getDay() == 7 && timeController.getTime().equals(resetTime)) {
                addRow(2);
            }
        }
    }
}
