import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {UserService} from '../../services/user.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-navigation-bar',
  imports: [
    RouterLink,
    NgIf
  ],
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.css'
})
export class NavigationBarComponent implements OnInit{

  isLoggedIn= false;
  username: string | null =null;

  constructor(
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.userService.isLoggedIn();
    this.username = this.userService.getUsername();


    this.userService.isLoggedIn$.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
      if (loggedIn) {
        this.username = this.userService.getUsername();
      } else {
        this.username = null;
      }
    });
  }

  logout(): void {
    this.userService.logout().subscribe({
      next: () => {
        this.router.navigate(['/login']).then(() => {
          console.log('Wylogowano i przekierowano do strony logowania');
        });
      },
      error: (error) => {
        console.error('Błąd podczas wylogowywania:', error);
      }
    });
  }

}
