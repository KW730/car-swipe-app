package car.app.web.car_swipe_webapp.scraping;

import car.app.web.car_swipe_webapp.model.Car;
import car.app.web.car_swipe_webapp.repository.CarRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarScraping {

    private  static CarRepository carRepository;
    public CarScraping(CarRepository carRepository){
        CarScraping.carRepository = carRepository;
    }

    private final static String BASE_URL = "https://www.otomoto.pl/osobowe?page=1";

    public static Elements scrapeCarArticles(){

        Elements scrapedArticles = new Elements();

            try {
                String url = BASE_URL;
                Document doc = Jsoup.connect(url).get();
                Elements articles = doc.select("article[data-id]");

                if (articles.isEmpty()){
                    System.out.println("No more pages left to scrap.");
                }else {
                    scrapedArticles.addAll(articles);
                }
            } catch (Exception e) {
                System.out.println("Error while scraping: " + e.getMessage());
                return new Elements();
            }
        return scrapedArticles;
    }

    public static List<Car> convertValuesFromArticles(Elements articles){

        List<Car> cars = new ArrayList<>();

        for (Element article : articles){
            try {

                String advertTitle = article.selectFirst("a[href]").text();
                String engineAndPower = article.select("p").get(0).text();
                String mileage = article.select("dd").get(0).text();
                String fuelType = article.select("dd").get(1).text();
                String gearbox = article.select("dd").get(2).text();
                String productionY = article.select("dd").get(3).text();
                String carPrice = article.select("h3").get(0).text();
                String imageUrl = article.select("img").attr("src").toString();

                String[] titleSplit = advertTitle.split(" ");
                String brandAndModel = Arrays.stream(titleSplit)
                        .limit(3)
                        .collect(Collectors.joining(" "));

                String[] engineAndPowerSplit = engineAndPower.split("•");
                String engineCapacity = engineAndPowerSplit.length > 0 ? engineAndPowerSplit[0].trim() : "N/A";
                String enginePower = engineAndPowerSplit.length > 0 ? engineAndPowerSplit[1].trim() : "N/A";

                int productionYear = Integer.parseInt(productionY);
                carPrice = carPrice.replaceAll("[^\\d.,-]", "")
                        .replace(" ", "")
                        .replace(",", ".");
                double price = Double.parseDouble(carPrice);

                Car car = new Car(brandAndModel, imageUrl, productionYear, enginePower, engineCapacity,
                        gearbox, mileage, fuelType, price);
                cars.add(car);
            }catch (Exception e){
                System.out.println("Error while converting: " + e.getMessage());
            }
        }
        return cars;
    }

    public  static void saveCarsToDatabase(List<Car> cars){
        for (Car car : cars){
            try {
                carRepository.save(car);
            } catch (Exception e) {
                System.out.println("Error while saving car to database: " + e.getMessage());
            }
        }
    }
}
