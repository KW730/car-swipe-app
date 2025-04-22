import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {Car} from '../models/car';
import {UserService} from './user.service';


@Injectable({
  providedIn: 'root'
})
export class CarService {
  private apiUrl = "http://localhost:8080/api/cars";


  constructor(private http: HttpClient) {}

  getRandomCar(userId: number | null) : Observable<Car>{
    return this.http.get<Car>(`${this.apiUrl}/random/${userId}`).pipe(
      map(data => new Car(data.id, data.make, data.imageUrl, data.productionYear, data.power,
        data.engine, data.gearbox, data.mileage, data.fuel, data.price))
    );
  }

  addCarToFavourites(userId: number | null, carId: number) : Observable<Car>{
    return this.http.post<Car>(`${this.apiUrl}/like?clientId=${userId}&carId=${carId}`, {});
  }

  rejectCar(userId: number | null, carId: number) : Observable<Car>{
    return this.http.post<Car>(`${this.apiUrl}/reject?clientId=${userId}&carId=${carId}`, {});
  }

  displayFavouriteCars(userId: number) : Observable<Car[]>{
    return this.http.get<Car[]>(`${this.apiUrl}/favourites?clientId=${userId}`).pipe(
      map(cars => cars.map(car => new Car(car.id, car.make, car.imageUrl, car.productionYear,
        car.power, car.engine, car.gearbox, car.mileage, car.fuel, car.price)))
    );
  }

}
