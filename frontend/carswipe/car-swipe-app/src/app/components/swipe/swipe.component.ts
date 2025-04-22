import {Component, OnInit} from '@angular/core';
import {Car} from '../../models/car';
import {CarService} from '../../services/car.service';
import {UserService} from '../../services/user.service';
import {NgIf} from '@angular/common';
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-swipe',
  imports: [
    NgIf
  ],
  templateUrl: './swipe.component.html',
  styleUrl: './swipe.component.css',
  animations:[
    trigger('swipeAnimation', [
      state('none', style({ transform: 'translateX(0)' })),
      state('left', style({ transform: 'translateX(-100%) rotate(-15deg)', opacity: 0 })),
      state('right', style({ transform: 'translateX(100%) rotate(15deg)', opacity: 0 })),
      transition('* => left', [animate('300ms ease-out')]),
      transition('* => right', [animate('300ms ease-out')]),
    ])
  ]
})
export class SwipeComponent implements OnInit{

  car: Car | null = null;
  userId!: number | null;

  constructor(
    private carService: CarService,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.userId = this.userService.getUserId();
    this.getRandomCar();
  }

  getRandomCar() {
      this.carService.getRandomCar(this.userId).subscribe({
        next: (car:Car)=>this.car = car,
        error: () => this.car = null
      });
    }

    likeCar() {
    if (!this.car) return;
        this.carService.addCarToFavourites(this.userId, this.car.id).subscribe(() =>
        this.getRandomCar());
    }

    rejectCar() {
    if (!this.car) return;
        this.carService.rejectCar(this.userId, this.car.id).subscribe(() =>
          this.getRandomCar());

    }
  }


