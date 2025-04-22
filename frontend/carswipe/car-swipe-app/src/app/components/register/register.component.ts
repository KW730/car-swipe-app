import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {User} from '../../models/user';
import {NgClass} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registrationForm: FormGroup;
  isSubmitting = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.registrationForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(): void {
    if (this.registrationForm.invalid) {
      return;
    }

    const { username, email, password } = this.registrationForm.value;

    this.isSubmitting = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.userService.register(username, email, password)
      .subscribe({
        next: (user: User) => {
          this.isSubmitting = false;
          this.successMessage = 'Rejestracja zakończona pomyślnie!';
          this.registrationForm.reset();
          this.router.navigate(['login']).then(r =>
          console.log("Przeniesiono do strony logowania")
          )
        },
        error: (error) => {
          this.isSubmitting = false;
          this.errorMessage = error.error?.message || 'Wystąpił błąd podczas rejestracji.';
        }
      });
  }
}
