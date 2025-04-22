package car.app.web.car_swipe_webapp.controller;

import car.app.web.car_swipe_webapp.model.Client;
import car.app.web.car_swipe_webapp.service.implementation.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class ClientController {

    ClientServiceImpl clientService;
    AuthenticationManager authenticationManager;

    public ClientController(ClientServiceImpl clientService){
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody Client client){
        try {
            clientService.registerClient(client.getClientEmail(), client.getPassword(), client.getUsername());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Użytkownik zarejestrowany pomyślnie");
            response.put("username", client.getUsername());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginClient(@RequestBody Client client) {
        String email = client.getClientEmail();
        String password = client.getPassword();

        String token = clientService.login(email, password);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Błędny login lub hasło");
        }
        Client loggedClient = clientService.getClientByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", loggedClient.getUsername());
        response.put("userId", loggedClient.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutClient(HttpServletRequest request){
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null){
            httpSession.invalidate();
        }
        SecurityContextHolder.clearContext();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Wylogowano pomyślnie !");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentClient(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication .isAuthenticated() ||
        authentication.getPrincipal().equals("anonymous")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Niezalogowany"));
        }

        Map<String, String> response = new HashMap<>();
        response.put("username", authentication.getName());
        return ResponseEntity.ok(response);
    }
}

