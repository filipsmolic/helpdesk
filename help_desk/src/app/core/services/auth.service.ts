import { Injectable } from '@angular/core'
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode';
import { decode } from 'punycode';
import { DecodedToken } from '../interfaces/token.interface';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private cookieService: CookieService) { }

    setToken(access_token: string) {
        this.cookieService.set("access_token", access_token, { 
            path: '/',
            secure: true,
        });
    }

    getToken(): string | null {
        return this.cookieService.get("access_token") || null;
    }

    clearToken() {
        this.cookieService.delete("access_token", '/');
    }

    getUsername(): string {
        let token = this.getToken();
        if (token) {
            let decodedToken = jwtDecode<DecodedToken>(token);
            return decodedToken.sub;
        }
        return '';
    }

    getUserRoles(): string[] {
        let token = this.getToken();
        if (token) {
            let decodedToken = jwtDecode<DecodedToken>(token);
            return decodedToken.roles;
        }
        return [];
    }

}