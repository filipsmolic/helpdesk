import { Component } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import {ReactiveFormsModule, FormControl, FormGroup, Validators, FormsModule } from '@angular/forms';
import { LoginService } from '../../core/services/login.service';
import { AuthService } from '../../core/services/auth.service';
import { RoutesService } from '../../core/services/routes.service';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [NgOptimizedImage, ReactiveFormsModule ],
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  formSubmited = false;
  loginFailed = false;

  constructor(private loginService: LoginService, private routesService: RoutesService, private authService: AuthService) {

  }

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });

  handleSubmit() {

    this.formSubmited = true;
    this.loginForm.markAllAsTouched();

    if (this.loginForm.valid) {
      let loginData = {
        username: this.loginForm.get('username')?.value?.toLowerCase(),
        password: this.loginForm.get('password')?.value,
      };

      this.loginService.login(loginData).subscribe({
        next: (response)  => {
            this.authService.setToken(response.accessToken);
            this.routesService.navigateHome();
        }, 
        error: (err) => {
            console.log('Error in login:', err.message);
            this.loginFailed=true;
            this.formSubmited=false;
            this.loginForm.reset({
              username: '',
              password: ''
            }, { emitEvent: false });
        }
      })
    } else {
      console.log("Invalid form")
    }
  }

}
