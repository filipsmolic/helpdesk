import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class RoutesService {

    constructor (private router: Router) {

    }

    refreshRoute() {
        const currentUrl = this.router.url;
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigateByUrl(currentUrl);
        });
    }

    navigateHome() {
        this.router.navigate(['/home']);
    }

    navigateToUsers() {
        this.router.navigate(['/users']);
    }

    navigateToLogin() {
        this.router.navigate(['/login']);
    }

    currentUrl(): String {
        return this.router.url;
    }

}