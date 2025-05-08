import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-change',
  imports: [FormsModule,CommonModule],
  templateUrl: './change.component.html',
  styleUrl: './change.component.css'
})
export class ChangeComponent {
  

    http = inject(HttpClient)
    router = inject(Router)
    user={
      username:'',
      password: '',
      confirmPassword:''
    }
    changePassword(){
      if(this.user.password != this.user.confirmPassword ){
        this.router.navigateByUrl("user");
        alert ("passwords are not the same");
        
        return;
      }
      this.http.patch("http://localhost:8080/password",this.user).subscribe((res:any)=>{
        
        this.router.navigateByUrl("users");
        alert("password change");
      }
      ,error=>{
      
        alert("error changing password");
      })
      }
    }

  


