import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, tap, throwError } from 'rxjs';
import { jwtDecode } from 'jwt-decode'; // Correct import statement

@Injectable({
  providedIn: 'root',
})
export class UserServicesService {
  userId: number | undefined;
  // userId: number | undefined;
  constructor(private http: HttpClient) {}

  private apiUrl = '/user';
  private gateway = '/gateway/user';

  // This is where you might save the JWT token after login
  private tokenKey = 'auth-token';

  getUsers(): Observable<any[]> {
    console.log('Fetching users from:', `${this.apiUrl}/getAll`); // Debug log
    return this.http.get<any[]>(`${this.apiUrl}/getAll`);
  }

  // registerUser(userData: any): Observable<any> {
  //   return this.http.post(`${this.apiUrl}/register`, userData, {
  //     responseType: 'text',
  //   });
  // }
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
    return this.http
      .post(`${this.apiUrl}/loginReq`, adminData, {
        responseType: 'json', // Ensure it's expecting JSON

        // });
      })
      .pipe(
        tap((response: any) => {
          console.log('API Response:', response);
          this.setToken(response.token);
        })
        // Log the response here
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

  getUserById(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/getById/${userId}`);
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

  getUserIdFromToken(): Observable<number> {
    const token = localStorage.getItem('token');
    console.log('token : in getUserIdFromToken : ' + token);

    if (!token) {
      return throwError(() => new Error('No token found'));
    }

    try {
      const decodedToken: any = jwtDecode(token); // Decode the JWT token

      console.log('real Token : ' + token);

      console.log('Decoded Token : ' + decodedToken);
      // console.log("id from toke is here : "+decodedToken.id);
      const email = decodedToken.sub;
      console.log('User email from token:', email);
      this.http
        .get<number>(`${this.apiUrl}/getuserIdByEmail/${email}`)
        .subscribe({
          next: (id) => {
            this.userId = id;
            console.log('User id Is : ' + this.userId);
          },
          error: (err) => console.error('user Id fetch failed : ', err),
        });

      // return decodedToken.id; // Extract the userId from the decoded token

      return this.http.get<number>(`${this.apiUrl}/getuserIdByEmail/${email}`);
    } catch (error) {
      console.error('Error decoding token:', error);
      return throwError(() => new Error('Invalid token'));
    }
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

  //send otp
  sendOTP(email: string): Observable<string> {
    const params = new HttpParams().set('email', email);
    return this.http.post(`${this.apiUrl}/send-otp`, null, {
      params,
      responseType: 'text',
    });
  }

  // 2. Verify OTP
  verifyOtp(email: string, otp: string): Observable<string> {
    const params = new HttpParams().set('email', email).set('otp', otp);
    return this.http.post(`${this.apiUrl}/verify-otp`, null, {
      params,
      responseType: 'text',
    });
  }
}
