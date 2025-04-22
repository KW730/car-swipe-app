package car.app.web.car_swipe_webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    @JsonProperty("email")
    private String clientEmail;
    @ManyToMany
    @JoinTable(
            name = "client_liked_cars",
            joinColumns = @JoinColumn (name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private Set<Car> likedCars = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "client_rejected_cars",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private Set<Car> rejectedCars = new HashSet<>();

    public Client(String username, String password, String clientEmail){
        this.username = username;
        this.password = password;
        this.clientEmail = clientEmail;

    }

    public void likeCar(Car car){
        likedCars.add(car);
    }

    public void rejectCar(Car car){
        rejectedCars.add(car);
        likedCars.remove(car);
    }

}
