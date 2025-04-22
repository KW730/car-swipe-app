package car.app.web.car_swipe_webapp.service;

import car.app.web.car_swipe_webapp.model.Client;

public interface ClientService {

    public Client registerClient(String email, String password, String username);
    public Client getClientByEmail(String email);
    public boolean checkPassword(Client client, String password);
    public String login(String email, String password);
}
