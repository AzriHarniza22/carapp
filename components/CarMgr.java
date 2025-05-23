package components;

import interfaces.ICarMgt;
import model.*;
import java.util.*;

public class CarMgr implements ICarMgt {
    private List<CarDetails> cars = new ArrayList<>();
    private Map<String, RentalDetails> reservations = new HashMap<>();
    private Map<String, String> reservationToCustomer = new HashMap<>();

    public CarMgr() {
        cars.add(new CarDetails("CAR001", "Toyota Avanza", "MPV", true, 350000));
        cars.add(new CarDetails("CAR002", "Honda Brio", "Hatchback", true, 250000));
    }

    public CarDetails[] getAvailableCars(String match) {
        return cars.stream()
            .filter(car -> car.model.toLowerCase().contains(match.toLowerCase()))
            .toArray(CarDetails[]::new);
    }

    public CarDetails getCarInfo(String carId) {
        return cars.stream().filter(car -> car.carId.equals(carId)).findFirst().orElse(null);
    }

    public String makeReservation(RentalDetails res, String custId) {
        String resRef = "RES" + new Random().nextInt(10000);
        reservations.put(resRef, res);
        reservationToCustomer.put(resRef, custId);
        return resRef;
    }

    public RentalDetails getReservation(String resRef) {
        return reservations.get(resRef);
    }

    public String startRental(String resRef) {
        RentalDetails res = reservations.get(resRef);
        if (res != null && !res.claimed) {
            res.claimed = true;
            return "Plate-" + res.carId;
        }
        return null;
    }

    public void addCar(CarDetails car) {
        cars.add(car);
    }

    public boolean editCar(String carId, String model, String type, Double price) {
        for (CarDetails car : cars) {
            if (car.carId.equals(carId)) {
                if (model != null) car.model = model;
                if (type != null) car.type = type;
                if (price != null) car.price = price;
                return true;
            }
        }
        return false;
    }

    public boolean deleteCar(String carId) {
        return cars.removeIf(car -> car.carId.equals(carId));
    }

    public List<CarDetails> listCars() {
        return cars;
    }
}