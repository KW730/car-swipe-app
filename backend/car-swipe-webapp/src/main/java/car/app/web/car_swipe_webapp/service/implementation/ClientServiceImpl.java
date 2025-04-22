package car.app.web.car_swipe_webapp.service.implementation;

import car.app.web.car_swipe_webapp.Security.JwtService;
import car.app.web.car_swipe_webapp.model.Client;
import car.app.web.car_swipe_webapp.repository.ClientRepository;
import car.app.web.car_swipe_webapp.service.ClientService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Client registerClient(String email, String password, String username) {
        if (clientRepository.findClientByClientEmail(email).isPresent() && clientRepository.findClientByUsername(username).isPresent()){
            throw new RuntimeException("Użytkownik o wprowadzonych danych już istnieje !");
        }
        String encodedPassword = passwordEncoder.encode(password);
        Client newClient = new Client(username,encodedPassword,email);
        return clientRepository.save(newClient);

    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findClientByClientEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));
    }

    @Override
    public boolean checkPassword(Client client, String rawPassword) {
        return passwordEncoder.matches(rawPassword, client.getPassword());
    }

    @Override
    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }

}
