import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserServicesService {
  constructor(private http: HttpClient) {}

  private apiUrl = '/user';
  private gateway =  '/gateway/user'


    // This is where you might save the JWT token after login
    private tokenKey = 'auth-token';

  getUsers(): Observable<any[]> {
    console.log('Fetching users from:', `${this.apiUrl}/getAll`); // Debug log
    return this.http.get<any[]>(`${this.apiUrl}/getAll`);
  }

  registerUser(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData, {
      responseType: 'text',
    });
  }

  // registerUser(userData: FormData): Observable<any> {
  //   return this.http.post(`${this.apiUrl}/register-with-image`, userData, {
  //     responseType: 'text',
  //   });
  // }

  // login(adminData:FormData):Observable<any>{
  //   return this.http.post(`${this.apiUrl}/loginReq`,adminData,{
  //     responseType:'text',
  //     // headers: new HttpHeaders({
  //     //   'Content-Type': 'application/json'
  //     // })
  //   })
  // }

  login(adminData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/loginReq`, adminData, {
      responseType: 'json',  // Ensure it's expecting JSON
    }).pipe(
      tap((response: any) => console.log('API Response:', response))  // Log the response here
    );
  }
  
  // Updated registerUser method
  // registerUser(userData: any, file: File): Observable<any> {
  //   // Create FormData to send both JSON and file
  //   const formData = new FormData();

  //   // Append the user data as a string (JSON format)
  //   formData.append('user', JSON.stringify(userData));

  //   // Append the file (image) data
  //   formData.append('file', file, file.name);

  //   // Send the FormData as a POST request to the backend
  //   return this.http.post(`${this.apiUrl}/register-with-image`, formData, {
  //     responseType: 'text', // Change responseType to 'text' as it seems your backend returns a simple message
  //   });
  // }


  deleteUser(userId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/deleteById/${userId}`);
  }

  getUserById(userId:number):Observable<any>{
    return this.http.get(`${this.apiUrl}/getById/${userId}`)
  }

  // uploadProfilePhoto(profilePhoto: File,id:number): Observable<any> {
  //   const formData = new FormData();
  //   formData.append('file', profilePhoto, profilePhoto.name);

  //   return this.http.post(`${this.apiUrl}/id/uploadProfilePhoto`, formData);
  // }

  uploadProfilePhoto(formData: FormData): Observable<any> {
    const url = `${this.apiUrl}/user/uploadProfileImage/`; // Adjust URL as needed
    return this.http.post(url, formData); // Send the FormData object containing the file and userId
  }



  getToken(): string {
    return localStorage.getItem('token') || ''; // Return the token from localStorage (or an empty string if not available)
  }
  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }
  clearToken(): void {
    localStorage.removeItem(this.tokenKey);
  }


}
