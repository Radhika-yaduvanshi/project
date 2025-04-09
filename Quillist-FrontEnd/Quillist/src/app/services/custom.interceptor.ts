import { HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { UserServicesService } from './user-services.service';
import { Observable } from 'rxjs';
import { inject, Injectable } from '@angular/core';

export const customInterceptor: HttpInterceptorFn = (req, next) => {
  // debugger;
   const loginservice=inject(UserServicesService);
   const currentUser = loginservice.getToken();

   if(currentUser){
    req=req.clone({
      setHeaders:{
        Authorization: `Bearer ${currentUser}`,
        'Content-Type': 'application/json'
      }
    })
   }

  return next(req);
};
// @Injectable()
// export class customInterceptor implements HttpInterceptor {
//   constructor(private loginService: UserServicesService) {}

//   intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     let currentUser = this.loginService.getToken();  // Method to get the JWT token
//     if (currentUser) {
//       request = request.clone({
//         setHeaders: {
//           'Authorization': `Bearer ${currentUser}`,
//           'Content-Type': 'application/json', // You can add other headers if necessary
//         }
//       });
//     }
//     return next.handle(request);
  // }
// }