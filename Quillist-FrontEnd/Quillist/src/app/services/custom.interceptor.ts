import { HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { UserServicesService } from './user-services.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

// export const customInterceptor: HttpInterceptorFn = (req, next) => {
//   // debugger;
//   const token = localStorage.getItem('token')
//   const cloneReq=req.clone({
//     setHeaders:{
//       Authorization: `Bearer ${token}`,
//     }
//   })
//   return next(cloneReq);
// };
@Injectable()
export class customInterceptor implements HttpInterceptor {
  constructor(private loginService: UserServicesService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let currentUser = this.loginService.getToken();  // Method to get the JWT token
    if (currentUser) {
      request = request.clone({
        setHeaders: {
          'Authorization': `Bearer ${currentUser}`,
          'Content-Type': 'application/json', // You can add other headers if necessary
        }
      });
    }
    return next.handle(request);
  }
}