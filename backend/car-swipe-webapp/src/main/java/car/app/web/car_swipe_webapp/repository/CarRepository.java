package car.app.web.car_swipe_webapp.repository;

import car.app.web.car_swipe_webapp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c NOT IN "
            + "(SELECT lc FROM Client cl JOIN cl.likedCars lc WHERE cl.id = :clientId) "
            + "AND c NOT IN "
            + "(SELECT rc FROM Client cl JOIN cl.rejectedCars rc WHERE cl.id = :clientId)")
    List<Car> carsNotRatedByUser(@Param("clientId") Long clientId);
}
