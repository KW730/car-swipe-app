package car.app.web.car_swipe_webapp.service;

import car.app.web.car_swipe_webapp.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Optional<Car> getRandomCar(Long clientId);
    Boolean addToFavouriteList(Long clientId, Long id);
    Boolean deleteCarFromFavouriteList(Long clientId, Long id);
    Boolean rejectCar(Long clientId, Long id);
    List<Car> displayFavouriteCars(Long clientId);
}
