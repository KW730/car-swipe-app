import { Routes } from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {RegisterComponent} from './components/register/register.component';
import {LoginComponent} from './components/login/login.component';
import {FavouriteCarsComponent} from './components/favourite-cars/favourite-cars.component';
import {SwipeComponent} from './components/swipe/swipe.component';

export const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'favourites', component: FavouriteCarsComponent},
  {path: 'swipe', component: SwipeComponent}
];
