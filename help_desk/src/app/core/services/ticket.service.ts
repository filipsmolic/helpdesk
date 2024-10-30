import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from 'rxjs';
import { Ticket } from '../interfaces/ticket.interface';
import { RoutesService } from './routes.service';

@Injectable({
    providedIn: 'root'
}) export class TicketService {
    
    constructor (private http: HttpClient, private authService: AuthService, private routesService: RoutesService) {}

    fetchTickets(roles: String[]): Observable<Ticket[]> | null {
        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        if (roles.includes("ROLE_ADMIN")) {
            return this.http.get<Ticket[]>('http://localhost:8080/api/admin/tickets', {headers})
                .pipe(
                    catchError((error) => {
                        console.error('Error caught:', error);
                        return throwError(() => new Error('Ticket fetching failed'));
                    })
                );
        } else if (roles.includes("ROLE_AGENT")) {
            return this.http.get<Ticket[]>('http://localhost:8080/api/agent/tickets', {headers})
                .pipe(
                    catchError((error) => {
                        console.error('Error caught:', error);
                        return throwError(() => new Error('Ticket fetching failed'));
                    })
                );
        } else if (roles.includes("ROLE_VIEWER")) {
            return this.http.get<Ticket[]>('http://localhost:8080/api/tickets', {headers})
                .pipe(
                    catchError((error) => {
                        console.error('Error caught:', error);
                        return throwError(() => new Error('Ticket fetching failed'));
                    })
                );
        } else {
            console.log("No suitable role");
            return null;
        }
    }

    createTicket(ticketData: Object) {
        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        this.http.post<Ticket>('http://localhost:8080/api/tickets', ticketData, {headers})
            .pipe(
                catchError((error) => {
                    console.error('Error caught:', error);
                    return throwError(() => new Error('Ticket creation failed'));
                })
            )
            .subscribe({
                next: (response) => {
                    console.log('Ticket created successfully:', response);
                    this.routesService.refreshRoute();
                },
                error: (err) => {
                    console.log('Error in subscription:', err.message); 
                }
            });
    }

    changeOpenStatus(id: number | undefined) {

        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        this.http.put<Ticket>(`http://localhost:8080/api/agent/tickets/start/${id}`, {}, {headers}).subscribe( response => {
            console.log(response);
        });

    }

    changeProgressStatus(id: number | undefined) {

        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        this.http.put<Ticket>(`http://localhost:8080/api/agent/tickets/close/${id}`, {}, {headers}).subscribe( response => {
            console.log(response);
        });

    }

    changeClosedStatus(id: number | undefined) {

        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        this.http.put<Ticket>(`http://localhost:8080/api/admin/tickets/${id}`, {}, {headers}).subscribe( response => {
            console.log(response);
        });

    }

    deleteTicket(id:number | undefined) {
        const token = this.authService.getToken();
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })

        this.http.delete<Ticket>(`http://localhost:8080/api/admin/tickets/${id}`, {headers}).subscribe( response => {
            console.log(response);
            this.routesService.refreshRoute();
        });
    }

}