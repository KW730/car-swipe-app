import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {CarService} from '../../services/car.service';
import {Car} from '../../models/car';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-favourite-cars',
  imports: [
    NgIf,
    NgForOf
  ],
  templateUrl: './favourite-cars.component.html',
  styleUrl: './favourite-cars.component.css'
})
export class FavouriteCarsComponent implements OnInit{

  userId!: number | null;
  favouriteCars: Car[] = [];

  constructor(private userService: UserService,
              private carService: CarService
  ) {}

  ngOnInit(): void {
    this.userId = this.userService.getUserId();
    if (this.userId){
      this.carService.displayFavouriteCars(this.userId).subscribe({
        next: (cars) => this.favouriteCars = cars,
        error: (err) => console.error('Błąd przy pobieraniu ulubionych aut')
      });
    }
  }




}
