import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../shared/components/navbar/navbar.component';
import { UserService } from '../../core/services/user.service';
import { Observable } from 'rxjs';
import { User } from '../../core/interfaces/user.interface';
import { AsyncPipe } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, ValidatorFn, Validators } from '@angular/forms';
import { UserComponent } from '../../shared/components/user/user.component';
import { TicketService } from '../../core/services/ticket.service';
import { RoutesService } from '../../core/services/routes.service';
import { AuthService } from '../../core/services/auth.service';
import { passwordValidator } from '../../core/validators/password-validator';
import { changePasswordValidator } from '../../core/validators/change-passwrod-validator';

@Component({
  selector: 'app-users-page',
  standalone: true,
  imports: [NavbarComponent, AsyncPipe, ReactiveFormsModule, UserComponent],
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css', '../home-page/home-page.component.css']
})
export class UsersPageComponent implements OnInit{

  users: User[] | null = null;
  newTicket: boolean = false;
  newUser: boolean = false;
  editUser:boolean = false;
  
  updateUser: User | null = null;

  formSubmited = false;
  checkBoxError = false;

  constructor(private userService: UserService, private ticketService:TicketService, private routesService: RoutesService, private authService: AuthService) {}

  ngOnInit(): void {
    this.userService.fetchUsers().subscribe({
      next: response => {
        response = response.filter(user => user.username !== this.authService.getUsername()).reverse();
        this.users = response;
      },
      error: (err) => {
        console.log("ERROR WHEN FETCHING USERS")
        this.routesService.navigateToLogin();
      }
    })
  }

  handleNewTicket(clicked: boolean) {
    console.log("clicked");
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

  userForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required, passwordValidator()]),
    roles: new FormGroup({
      ROLE_VIEWER: new FormControl(false),
      ROLE_AGENT: new FormControl(false),
      ROLE_ADMIN: new FormControl(false)
    })
  });

  getRoles(): String[] {
    const rolesFormGroup = this.userForm.get('roles') as FormGroup;
    return Object.keys(rolesFormGroup.controls)
                 .filter(role => rolesFormGroup.get(role)?.value);
  }

  handleNewUser(clicked: boolean) {
    this.newUser=true;
  }

  cancelNewUser() {
    this.newUser=false;
    this.checkBoxError=false;
    this.userForm.reset();
    this.formSubmited=false;
  }

  handleUserSubmit() {

    this.formSubmited = true;
    this.userForm.markAllAsTouched();

    if (this.getRoles().length == 0) {
      this.checkBoxError = true;
      return;
    }

    if (this.userForm.valid) {

      let userData = {
        username: this.userForm.get('username')?.value?.toLowerCase(),
        password: this.userForm.get('password')?.value,
        roles: this.getRoles(),
      };

      this.userService.createUser(userData);

      console.log(userData);

    } else {
      console.log("Invalid form")
    }

  }

  updateUserForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(null, [changePasswordValidator()]),
    roles: new FormGroup({
      ROLE_VIEWER: new FormControl(false),
      ROLE_AGENT: new FormControl(false),
      ROLE_ADMIN: new FormControl(false)
    })
  });

  getUpdatedRoles(): String[] {
    const rolesFormGroup = this.updateUserForm.get('roles') as FormGroup;
    return Object.keys(rolesFormGroup.controls)
                 .filter(role => rolesFormGroup.get(role)?.value);
  }

  handleEditUser(user: User) {
    this.editUser=true;
    this.updateUser = user;

    this.updateUserForm.get('username')?.setValue(user.username);

    if (user.roles.some(r => r.name === 'ROLE_VIEWER')) {
      this.updateUserForm.get('roles')?.get('ROLE_VIEWER')?.setValue(true);
    }
    if (user.roles.some(r => r.name === 'ROLE_AGENT')) {
      this.updateUserForm.get('roles')?.get('ROLE_AGENT')?.setValue(true);
    }
    if (user.roles.some(r => r.name === 'ROLE_ADMIN')) {
      this.updateUserForm.get('roles')?.get('ROLE_ADMIN')?.setValue(true);
    }

  }

  cancelEditUser() {
    this.editUser=false;
    this.checkBoxError=false;
    this.updateUserForm.reset();
  }

  handleEditUserSubmit() {

    this.updateUserForm.markAllAsTouched();

    if (this.getUpdatedRoles().length == 0) {
      this.checkBoxError = true;
      return;
    }

    if (this.updateUserForm.valid) {

      let updateUserData = {
        username: this.updateUserForm.get('username')?.value,
        password: this.updateUserForm.get('password')?.value,
        roles: this.getUpdatedRoles(),
      };
  
      this.userService.updateUser(updateUserData);

    } else {
      console.log("Invalid form")
    }

  }

}
