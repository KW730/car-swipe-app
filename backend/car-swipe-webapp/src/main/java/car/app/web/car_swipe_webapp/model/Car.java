package car.app.web.car_swipe_webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    @Column(unique = true)
    private String imageUrl;
    private int productionYear;
    private String power;
    private String engine;
    private String gearbox;
    private String mileage;
    private String fuel;
    private double price;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedCars")
    private Set<Client> likedByClients = new HashSet<>();

    public Car(){};

    public Car(String make, String imageUrl, int productionYear,
               String power,String engine,String gearbox,
               String mileage, String fuel, double price){
        this.make = make;
        this.imageUrl = imageUrl;
        this.productionYear = productionYear;
        this.power = power;
        this.engine = engine;
        this.gearbox = gearbox;
        this.mileage = mileage;
        this.fuel = fuel;
        this.price = price;
    };

}
