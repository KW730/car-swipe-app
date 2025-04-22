package car.app.web.car_swipe_webapp.controller;

import car.app.web.car_swipe_webapp.model.Car;
import car.app.web.car_swipe_webapp.model.Client;
import car.app.web.car_swipe_webapp.service.ClientService;
import car.app.web.car_swipe_webapp.service.implementation.CarServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cars")
public class CarController {

    private final CarServiceImpl carService;
    private final ClientService clientService;

    public CarController(CarServiceImpl carService, ClientService clientService) {
        this.carService = carService;
        this.clientService = clientService;
    }


    @GetMapping("/random/{clientId}")
    public ResponseEntity<Car> getRandomCar(@PathVariable Long clientId){
        return carService.getRandomCar(clientId)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.noContent().build());
    }

    @PostMapping("/like")
    public ResponseEntity<Map<String, String>> likeCar (@RequestParam Long clientId, @RequestParam Long carId){
        boolean success = carService.addToFavouriteList(clientId, carId);
        if (success){
            return ResponseEntity.ok(Map.of("message", "Doddano do ulubionych"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Nie znaleziono użytkownika lub auta"));
    }

    @PostMapping("/reject")
    public ResponseEntity<Map<String, String>> rejectCar(@RequestParam Long clientId, @RequestParam Long carId){
        boolean success = carService.rejectCar(clientId, carId);
        if (success){
            return ResponseEntity.ok(Map.of("message", "Odrzucono auto"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Nie znaleziono użytkownika lub auta"));
    }

    @GetMapping("/favourites")
    public ResponseEntity<List<Car>> displayFavouriteCars(@RequestParam Long clientId){
        List<Car> favourites = carService.displayFavouriteCars(clientId);
        return ResponseEntity.ok(favourites);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteCarFromFavourites (@RequestParam Long clientId, Long carId){
        boolean success = carService.deleteCarFromFavouriteList(clientId, carId);
        if (success){
            ResponseEntity.ok(Map.of("message", "Usunięto auto"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "bład usuwania"));
    }
}
