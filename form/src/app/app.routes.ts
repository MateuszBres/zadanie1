import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { UserComponent } from './components/user/user.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ChangeComponent } from './components/change/change.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'user', component: UserComponent },
  {path:"registration", component: RegistrationComponent},
  {path:"change", component: ChangeComponent},
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];
