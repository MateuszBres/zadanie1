import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  imports: [CommonModule,FormsModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {
  
  http = inject(HttpClient);
  router = inject(Router);
  user = {
    name: '',
    lastname:'',
    username:'',
    email:''
  }
    register() {
      this.http.post('http://localhost:8080/register', this.user).subscribe({
        next: (res: any) => {
          this.router.navigateByUrl('/user');
          alert("User created");
        },
        error: (err) => {
          if (err.status === 409 && err.error?.message) {
            alert(err.error.message);
          } else {
            alert("Unexpected error during registration.");
          }
        }
      });



  }
  

}
