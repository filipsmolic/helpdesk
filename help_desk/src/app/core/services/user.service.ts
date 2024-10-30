import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { User } from '../interfaces/user.interface';
import { RoutesService } from './routes.service';

@Injectable({
    providedIn: 'root'
}) export class UserService {

    constructor (private http: HttpClient, private authService: AuthService, private routesService: RoutesService) {}

    fetchUsers(): Observable<User[]> {
        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        return this.http.get<User[]>('http://localhost:8080/api/users', {headers}).pipe(
            catchError((error) => {
                console.error('Error caught:', error);
                return throwError(() => new Error('User fetching failed'));
            })
        );
        
    }

    disableUser(id:number | undefined) {
        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        try {
            this.http.put<User>(`http://localhost:8080/api/users/disable/${id}`, {}, {headers}).subscribe( response => {
                console.log(response);
            });
        } catch (error) {
            console.log("Error disabling user");
        }
    }

    enableUser(id: number | undefined) {
        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        try {
            this.http.put<User>(`http://localhost:8080/api/users/enable/${id}`, {}, {headers}).subscribe( response => {
                console.log(response);
            });
        } catch (error) {
            console.log("Error enabling user");
        }
    }

    createUser(userData: Object) {
        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        this.http.post<User>('http://localhost:8080/api/users', userData, {headers})
            .pipe(
                catchError((error) => {
                    console.error('Error caught:', error);
                    return throwError(() => new Error('User creation failed'));
                })
            )
            .subscribe({
                next: (response) => {
                    console.log('User created successfully:', response);
                    this.routesService.refreshRoute();
                },
                error: (err) => {
                    console.log('Error in subscription:', err.message); 
                }
            });
    }

    updateUser(userData: Object) {
        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        this.http.put<User>('http://localhost:8080/api/users', userData, {headers})
            .pipe(
                catchError((error) => {
                    console.error('Error caught:', error);
                    return throwError(() => new Error('User update failed'));
                })
            )
            .subscribe({
                next: (response) => {
                    console.log('User updated successfully:', response);
                    this.routesService.refreshRoute();
                },
                error: (err) => {
                    console.log('Error in subscription:', err.message); 
                }
            });
    }


}