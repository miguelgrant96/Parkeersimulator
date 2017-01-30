package Views;

import Controllers.*;
import Models.*;


import javax.swing.*;
import java.awt.*;

public class SimulatorView  extends AbstractView{
    //extra uitbreiding op de GUI tijd en omzet
    private final String PASS = "Pass: ";
    private final String ADHOC = "Adhoc: ";

    private CarParkView carParkView;
    //   private JLabel time;
    private JLabel adhoc;
    private JLabel pass;
    private Container contentPane;
    private TimeController klok;
    private CarController garage;
    private GarageStats stats;
    private SimulatorController simulatorController;

    public SimulatorView(SimulatorController simulatorController) {
        this.simulatorController = simulatorController;
        garage = simulatorController.getGarageController();
        carParkView = new CarParkView();
        stats = simulatorController.getGarageStats();

        //      time = new JLabel(TIME_TEKST, JLabel.CENTER);
        adhoc = new JLabel(ADHOC, JLabel.CENTER);
        pass = new JLabel(PASS, JLabel.CENTER);
        //  contentPane = getContentPane();

        add(carParkView, BorderLayout.CENTER);
        //     add(time, BorderLayout.NORTH);
        add(adhoc, BorderLayout.SOUTH);
        add(pass, BorderLayout.NORTH);
        setVisible(true);

        //      updateView();
    }

    public void updateView() {
        //      time.setText(TIME_TEKST + klok.getTime());
        carParkView.updateView();
        showStatus();
    }

    public void showStatus()
    {
        if(!isVisible()) {
            setVisible(true);
        }

        //stats.countFinished();
        //stats.reset();
        pass.setText(PASS + garage.getPass().getCount());
        adhoc.setText(ADHOC + garage.getAdhoc().getCount());
    }


    private class CarParkView extends JPanel {

        private Dimension size;
        private Image carParkImage;

        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }

        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }

        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }

            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }

        public void updateView() {
            // Create a new car park image if the size has changed.
            //garage.tick();
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < garage.getNumberOfFloors(); floor++) {
                for(int row = 0; row < garage.getNumberOfRows(); row++) {
                    for(int place = 0; place < garage.getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = simulatorController.getGarageController().getCarAt(location);
                        if (floor == 0) {
                            Color color = car == null ? Color.yellow : car.getColor();
                            drawPlace(graphics, location, color);
                        } else {
                            Color color = car == null ? Color.white : car.getColor();
                            drawPlace(graphics, location, color);
                        }
                    }
                }
            }
            repaint();

        }

        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}
