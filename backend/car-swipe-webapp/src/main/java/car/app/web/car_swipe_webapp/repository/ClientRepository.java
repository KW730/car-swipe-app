package car.app.web.car_swipe_webapp.repository;

import car.app.web.car_swipe_webapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByClientEmail(String email);
    Optional<Client> findClientByUsername(String username);
    Optional<Client> findClientById(Long id);
}
