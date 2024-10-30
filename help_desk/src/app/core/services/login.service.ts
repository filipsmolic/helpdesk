import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { catchError, Observable, throwError } from 'rxjs';
import { RoutesService } from './routes.service';

@Injectable({
    providedIn: 'root'
})
export class LoginService {

    constructor (private http: HttpClient, private routesService: RoutesService, private authService: AuthService) {

    }

    login(loginData: Object): Observable<{accessToken: string}> {
        return this.http.post<{ accessToken: string }>('http://localhost:8080/api/login', loginData)
        .pipe(
            catchError((error) => {
                console.error('Error caught:', error);
                return throwError(() => new Error('Login failed'));
            })
        );
    }

    logout() {
        this.authService.clearToken();
        this.routesService.navigateToLogin();
    }

}