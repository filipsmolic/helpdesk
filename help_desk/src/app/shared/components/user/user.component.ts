import { Component, Input, Output, EventEmitter } from '@angular/core';
import { User } from '../../../core/interfaces/user.interface';
import { UpperCasePipe } from '@angular/common';
import { UserService } from '../../../core/services/user.service';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [UpperCasePipe],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent {

  @Output()
  newEditEvent = new EventEmitter<User>();

  @Input() 
  user_: User | undefined;

  constructor(private userService: UserService) {}

  disableUser(id: number | undefined) {

    this.userService.disableUser(id);
    if (this.user_) {
      this.user_.enabled=false;
    }

  }

  enableUser(id: number | undefined) {

    	this.userService.enableUser(id);
      if (this.user_) {
        this.user_.enabled=true;
      }

  }

  editRolesFunction() {
    this.newEditEvent.emit(this.user_);
  }


}
