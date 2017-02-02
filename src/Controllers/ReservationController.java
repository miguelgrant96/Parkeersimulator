package Controllers;

import Models.AdHocCar;
import Models.Reservation;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Arjen on 1-2-2017.
 */
public class ReservationController extends AbstractController{

    private List<Reservation> reservations;
    private TimeController timeController;
    private CarController carController;

    public ReservationController()
    {
        reservations = new ArrayList<>();
        timeController = (TimeController) super.registeryController.getObjectInstance("TimeController");
        carController = (CarController) super.registeryController.getObjectInstance("CarController");
    }

    public void addReservation(Reservation reservation)
    {
        reservation.setLocation(carController.getFirstPaidLocation());
        carController.setCarAt(reservation.getLocation(), reservation);
        reservations.add(reservation);
    }

    public void checkReservations()
    {
        Reservation removeObj = null;
        for(Reservation res : reservations)
        {
            if(timeController.getDayOfYear() == res.getDayOfArrival() && timeController.getHour() == res.getHourOfArrival() && timeController.getMinute() == res.getMinuteOfArrival())
            {
                carController.removeCarAt(res.getLocation());
                carController.setCarAt(res.getLocation(), new AdHocCar());
                removeObj = res;

            }
        }
        reservations.remove(removeObj);
    }
}
