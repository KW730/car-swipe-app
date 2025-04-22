package car.app.web.car_swipe_webapp.Security;

import car.app.web.car_swipe_webapp.model.Client;
import car.app.web.car_swipe_webapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findClientByClientEmail(email);

        if (client.isEmpty()){
            throw new UsernameNotFoundException("Nie znaleziono użytkownika");
        }
        Client client1 = client.get();

        System.out.println("Logowanie użytkownika: " + email);

        return new org.springframework.security.core.userdetails.User(
                client1.getClientEmail(),
                client1.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("USER"))
        );
    }
}
