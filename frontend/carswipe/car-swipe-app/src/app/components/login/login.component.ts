import { Component } from '@angular/core';
import {UserService} from '../../services/user.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {User} from '../../models/user';
import {NgClass, NgIf} from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [
    NgIf,
    ReactiveFormsModule,
    NgClass,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm: FormGroup;
  isSubmitting = false;
  errorMessage = '';


  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router
  ) {

    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit(){
    if (this.loginForm.invalid){
      return;
    }

    const {email, password} = this.loginForm.value;

    this.isSubmitting = true;
    this.errorMessage = '';

    this.userService.login(email, password)
      .subscribe({
        next: (user: User)=> {
          this.isSubmitting = false;
          this.router.navigate(['/home']).then(r =>
          console.log("Przekierowanie do strony głównej")
          );
    },
        error: (error) =>{
          this.isSubmitting = false;
          this.errorMessage = error.error?.message || 'Wystąpił błąd podczas logowania !';
        }
      })
  }
}
