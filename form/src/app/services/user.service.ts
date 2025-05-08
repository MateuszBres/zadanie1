import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  id: number;
  name: string;
  lastname: string;
  username: string;
  password: string;
  email: string;
  registrationDate: Date;
  status: 'ACTIVE' | 'INACTIVE' | 'BANNED';
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/users';

  http = inject(HttpClient) 

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }
}
