import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserServicesService {
  constructor(private http: HttpClient) {}

  private apiUrl = '/user';

  getUsers(): Observable<any[]> {
    console.log('Fetching users from:', `${this.apiUrl}/getAll`); // Debug log
    return this.http.get<any[]>(`${this.apiUrl}/getAll`);
  }

  // registerUser(userData: FormData): Observable<any> {
  //   return this.http.post(`${this.apiUrl}/register`, userData, {
  //     responseType: 'text',
  //   });
  // }

  registerUser(userData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/register-with-image`, userData, {
      responseType: 'text',
    });
  }

  deleteUser(userId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/deleteById/${userId}`);
  }
}
