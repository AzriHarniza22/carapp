package components;

import interfaces.*;
import model.*;

public class ReservationSystem implements IMakeReservation, IStartRental {
    private ICarMgt carMgr;
    private ICustomerMgt custMgr;
    private IBilling billing;

    public ReservationSystem(ICarMgt carMgr, ICustomerMgt custMgr, IBilling billing) {
        this.carMgr = carMgr;
        this.custMgr = custMgr;
        this.billing = billing;
    }

    public String reserveCar(String customerId, String carId, DateRange dateRange) {
        CarDetails car = carMgr.getCarInfo(carId);
        if (car == null || !car.available) throw new IllegalArgumentException("Mobil tidak tersedia");

        RentalDetails rental = new RentalDetails(carId, dateRange);
        String resRef = carMgr.makeReservation(rental, customerId);
        billing.generateBill(customerId, resRef, car.price);
        return resRef;
    }

    public String startCarRental(String resRef) {
        return carMgr.startRental(resRef);
    }
}