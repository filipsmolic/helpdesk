import { TicketService } from './../../core/services/ticket.service';
import { NavbarComponent } from './../../shared/components/navbar/navbar.component';
import { AuthService } from '../../core/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Ticket } from '../../core/interfaces/ticket.interface';
import { AsyncPipe } from '@angular/common'
import { TicketComponent } from '../../shared/components/ticket/ticket.component';
import {ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../core/services/login.service';
import { Router } from '@angular/router';
import { RoutesService } from '../../core/services/routes.service';


@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [NavbarComponent, AsyncPipe, TicketComponent, ReactiveFormsModule],
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit{

  tickets: Ticket[] = [];
  newTicket: boolean = false;

  constructor(private authService: AuthService, private ticketService: TicketService, private routesService:RoutesService) {}

  ngOnInit(): void {
    this.ticketService.fetchTickets(this.authService.getUserRoles())?.subscribe({
        next: (response) => {
          this.tickets = response;
          console.log(response);
        },
        error: (err) => {
          this.routesService.navigateToLogin();
        }
      });
  }

  handleNewTicket(clicked: boolean) {
    this.newTicket=true;
  }

  cancelNewTicket() {
    this.newTicket=false;
  }

  ticketForm = new FormGroup({
    ticketDescription: new FormControl('', Validators.required),
  });

  handleTicketSubmit() {

    let ticketData = {
      ticketDescription: this.ticketForm.get('ticketDescription')?.value,
    };

    this.ticketService.createTicket(ticketData);
  }

}
