import { routes } from './../../../app.routes';
import { TicketService } from '../../../core/services/ticket.service';
import { LoginService } from '../../../core/services/login.service';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { RoutesService } from '../../../core/services/routes.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{

  @Output()
  newTicketEvent = new EventEmitter<boolean>();
  @Output()
  newUserEvent = new EventEmitter<boolean>();

  newUserButton: boolean = false;

  username: String = '';
  roles: String[] = [];

  constructor(private authService:AuthService, private loginService:LoginService, private routesService: RoutesService) {
    this.roles = this.authService.getUserRoles();
  }

  ngOnInit(): void {
    this.username = this.authService.getUsername().toUpperCase();
    if (this.routesService.currentUrl() == '/users') {
      this.newUserButton = true;
    } 
  }

  logoutUser() {
    this.loginService.logout();
  }

  handleNewTicket() {
    this.newTicketEvent.emit(true);
  }

  handleNewUser() {
    console.log("Clicked");
    this.newUserEvent.emit(true);
  }

  navigateHome() {
    this.routesService.navigateHome();
  }

  navigateToUsers() {
    this.routesService.navigateToUsers();
  }

}
