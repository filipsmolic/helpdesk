import { Component, Input } from '@angular/core';
import { Ticket } from '../../../core/interfaces/ticket.interface';
import { DatePipe } from '@angular/common';
import { TicketService } from '../../../core/services/ticket.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-ticket',
  standalone: true,
  imports: [ DatePipe ],
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent {
  @Input() 
  ticket_: Ticket | null = null;
  roles : String[] | null = null;

  constructor(private ticketService: TicketService, private authService: AuthService) {
    this.roles = this.authService.getUserRoles();
  }

  changeOpenStatus(id: number | undefined) {
    if (this.ticket_) {
      this.ticketService.changeOpenStatus(id);
      this.ticket_.status='in_progress';
    }
  }

  changeProgressStatus(id: number | undefined) {
    if (this.ticket_) {
      this.ticketService.changeProgressStatus(id);
      this.ticket_.status='closed';
    }
  }

  changeClosedStatus(id: number | undefined) {
    if (this.ticket_) {
      this.ticketService.changeClosedStatus(id);
      this.ticket_.status='open';
    }
  }

  deleteTicket(id: number | undefined) {
    if (this.ticket_) {
      this.ticketService.deleteTicket(id);
    }
  }
}
