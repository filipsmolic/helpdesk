import { Routes } from '@angular/router';
import { LoginPageComponent } from './features/login-page/login-page.component';
import { HomePageComponent } from './features/home-page/home-page.component';
import { UsersPageComponent } from './features/users-page/users-page.component';

export const routes: Routes = [
    {
        path: 'login',
        title: 'login-page',
        component: LoginPageComponent,
    },
    {
        path: 'home',
        title: 'home-page',
        component: HomePageComponent
    },
    {
        path: 'users',
        title: 'users-page',
        component: UsersPageComponent
    }
];
