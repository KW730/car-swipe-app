import { Component } from '@angular/core';
import {CarService} from '../../services/car.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private carService: CarService) {
  }

}
