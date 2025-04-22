package car.app.web.car_swipe_webapp.service.implementation;

import car.app.web.car_swipe_webapp.model.Car;
import car.app.web.car_swipe_webapp.model.Client;
import car.app.web.car_swipe_webapp.repository.CarRepository;
import car.app.web.car_swipe_webapp.repository.ClientRepository;
import car.app.web.car_swipe_webapp.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;

    @Override
    public Optional<Car> getRandomCar(Long clientId){
        List<Car> cars = carRepository.carsNotRatedByUser(clientId);
        if (cars.isEmpty()){
            return Optional.empty();
        }
        Random rnd = new Random();
        return Optional.of(cars.get(rnd.nextInt(cars.size())));
    }

    @Override
    @Transactional
    public Boolean addToFavouriteList(Long clientId, Long id) {
        Optional<Client> clientOpt = clientRepository.findClientById(clientId);
        Optional<Car> carOpt = carRepository.findById(id);

        if (clientOpt.isPresent() && carOpt.isPresent()){
            Client client = clientOpt.get();
            Car car = carOpt.get();

            client.likeCar(car);
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteCarFromFavouriteList(Long clientId, Long id) {
        Optional<Client> clientOpt = clientRepository.findClientById(clientId);
        Optional<Car> carOpt = carRepository.findById(id);

        if (clientOpt.isPresent() && carOpt.isPresent()){
            Client client = clientOpt.get();
            Car car = carOpt.get();

            if (client.getLikedCars().contains(car)){
                client.getLikedCars().remove(car);
                clientRepository.save(client);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean rejectCar(Long clientId, Long id) {
        Optional<Client> clientOpt = clientRepository.findClientById(clientId);
        Optional<Car> carOpt = carRepository.findById(id);

        if (clientOpt.isPresent() && carOpt.isPresent()){
            Client client = clientOpt.get();
            Car car = carOpt.get();

            client.rejectCar(car);
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Car> displayFavouriteCars(Long clientId) {
        return clientRepository.findClientById(clientId)
                .map(client -> new ArrayList<>(client.getLikedCars()))
                .orElseThrow(()-> new EntityNotFoundException("Client not found"));
    }
}
