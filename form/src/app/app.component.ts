import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router'; // dla router outlet
import { LoginComponent } from './components/login/login.component';
import { UserComponent } from './components/user/user.component';
import { CommonModule } from '@angular/common';
import { UserService, User} from './services/user.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LoginComponent, UserComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  users: User[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getUsers().subscribe(data => {
      this.users = data;
    });
  }
}
