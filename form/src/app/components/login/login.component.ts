import { Component, inject } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  imports: [FormsModule, CommonModule], 
})
export class LoginComponent {
  apiLogObj: any ={
  username: '',
  password: ''
  }

  router = inject(Router);
  http = inject(HttpClient);

  
  
  login() {
    this.http.post("http://localhost:8080/login",this.apiLogObj).subscribe((res:any)=>{
      localStorage.setItem("jwtToken",res.token);
      localStorage.setItem("username", res.username);
      this.router.navigateByUrl("user");
    },error=>{
      
      alert("Invalid username or password");
    }
    )

 }


  signUp(){
    this.router.navigateByUrl("/registration")
  }
}
