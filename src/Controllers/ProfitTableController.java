package Controllers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;

/**
 * Creates the table and its methods
 *
 * @author Jop Bakker
 * @version 1
 * @since 5-02-2016
 */
public class ProfitTableController extends AbstractController {

    private TimeController timeController;
    private PaymentController paymentController;

    private int called = 0;

    public DefaultTableModel model = new DefaultTableModel();

    public ProfitTableController(){
        //Connecting to the "Main" TimeController
        timeController = (TimeController) super.registeryController.getObjectInstance("TimeController");

    }

    public JTable createProfitTable(){
        model.addColumn("year");
        model.addColumn("Month");
        model.addColumn("Week");
        model.addColumn("Profit");

        JTable profitTable = new JTable(model);
        profitTable.setPreferredScrollableViewportSize(new Dimension(590,150));
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
    public String getMonthName(int i){
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

    /**
     * Method to remove all rows from the table
     */
    private void clearTable(){
        int rowCount = model.getRowCount();
        for(int i = rowCount - 1; i >= 0; i--) {
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

        Object[] data;
        switch(dataSet){
            //Case 1: Add year and month name to the table
            case 1:
                data = new Object[] {timeController.getYear(),getMonthName(timeController.getMonth()+1), "", ""};
                break;

            //Case 2: Add week number and weekly profits to the table
            case 2:
                data = new Object[] {"", "", timeController.getWeek(), getDecimalProfit(1)};
                break;

            //Case 3: Add monthly profit to the table
            case 3:
                data = new Object[] {"", "", "", getDecimalProfit(2)};
                break;

            //Default: Add blank line to the table
            default:
                data = new Object[] {"","", "", ""};
                break;
        }
        return data;
    }

    /**
     *
     * Method to save the table data to file
     */
    private void saveData(){
        try {
            File file = new File("C:\\Users\\jop\\Documents\\Table.txt"); //Loaction to save file + filename.extention
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i = 0; i < model.getRowCount(); i++){
                for(int j = 0; j < model.getColumnCount(); j++){
                    bw.write(model.getValueAt(i,j)+"  ");
                }
                bw.write("\n_________________\n");
            }
            bw.close();
            fw.close();
            JOptionPane.showMessageDialog(null, "Data saved");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateTable() {
        called++;
        String resetTime = "23:50";
        if (timeController.getMonth() == 12 && timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals(resetTime)) {
                addRow(2);
                addRow(3);
                saveData();

                paymentController.clearWeken();
                paymentController.clearMaanden();

                clearTable();
                model.addRow(new Object[]{timeController.getYear() + 1, getMonthName(1), "", ""});
            } else if (timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals(resetTime)) {
                addRow(2);
                addRow(3);

                                if(timeController.getMonth() == 1 && timeController.getWeek() == 4 && timeController.getDay() == 7 && timeController.getTime().equals(resetTime)){
                                    saveData();
                                }

                addRow(0);
                addRow(1);


                paymentController.clearWeken();
            } else if (timeController.getDay() == 7 && timeController.getTime().equals(resetTime)) {
                addRow(2);
            }
        }

    public void resetTable(){
        clearTable();
        model.addRow(new Object[]{timeController.getYear(),getMonthName(timeController.getMonth()), "", ""});
    }

}
