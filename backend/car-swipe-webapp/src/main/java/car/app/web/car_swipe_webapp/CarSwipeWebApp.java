package car.app.web.car_swipe_webapp;
import car.app.web.car_swipe_webapp.model.Car;
import car.app.web.car_swipe_webapp.scraping.CarScraping;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.IOException;
import java.util.List;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CarSwipeWebApp {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(CarSwipeWebApp.class, args);

		Elements scrapedArticles = CarScraping.scrapeCarArticles();
		List<Car> cars = CarScraping.convertValuesFromArticles(scrapedArticles);
		CarScraping.saveCarsToDatabase(cars);

	}

}
