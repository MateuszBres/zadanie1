import { Component, inject, OnInit } from '@angular/core';
import { UserService, User } from '../../services/user.service';
import { CommonModule } from '@angular/common';  
import { Router } from '@angular/router';


@Component({
  selector: 'app-user',
  standalone: true,
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  imports: [CommonModule]  
})
export class UserComponent implements OnInit {
  users: User[] = [];
  router = inject(Router)

  constructor(private userService: UserService) {
  }
  logout(){
    this.router.navigateByUrl("login");
  }
  change(){
    this.router.navigateByUrl("change")
  }

  ngOnInit(): void {
    this.userService.getUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (error) => {
        console.error('Error fetching users:', error);
      }
    });
  }
}
