import { HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest, HttpResponse } from '@angular/common/http';
import { UserServicesService } from './user-services.service';
// import { map, Observable } from 'rxjs';
import { inject, Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js'; 
import { map } from 'rxjs/operators';




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


  if (!req.url.includes('/loginReq')&& !req.url.includes('/register')  && req.body) {
    const encrypted = encryptData(req.body);
    console.log('Encrypted Body:', encrypted);  // 👀 Check browser console
    req = req.clone({
      body: encrypted,
    });
  }
  

  function encryptData(data: any): string {
    const secretKey = CryptoJS.enc.Utf8.parse('MySecretKey12345'); // 16-byte key
    const iv = CryptoJS.enc.Utf8.parse('MySecretKey12345'); // 16-byte IV

    
  
    const encrypted = CryptoJS.AES.encrypt(JSON.stringify(data), secretKey, {
      iv: iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7 // Use PKCS5Padding
    });
  
    return encrypted.toString(); // Base64-encoded ciphertext
  }


//   return next(req).pipe(
//     map((event: HttpEvent<any>) => {
//       if (event instanceof HttpResponse && typeof event.body === 'string') {
//         try {
//           // const decrypted = decryptData(event.body);
//           return event.clone({ body: decrypted });
//         } catch (error) {
//           console.error('Decryption failed:', error);
//         }
//       }
//       return event;
//     })
//   );
// };





// AES Decryption
// function decryptData(encryptedData: string): any {
//   const secretKey = 'MySecretKey123!';
//   const bytes = CryptoJS.AES.decrypt(encryptedData, secretKey);
//   const decryptedText = bytes.toString(CryptoJS.enc.Utf8);
//   return JSON.parse(decryptedText);
// }

// function decryptData(encryptedData: string): any {
//   const secretKey = CryptoJS.enc.Utf8.parse('MySecretKey123456'); // MATCH Java
//   const iv = CryptoJS.enc.Utf8.parse('MySecretKey123456'); // MATCH Java

//   const decrypted = CryptoJS.AES.decrypt(encryptedData, secretKey, {
//     iv: iv,
//     mode: CryptoJS.mode.CBC,
//     padding: CryptoJS.pad.Pkcs7
//   });

//   const decryptedText = decrypted.toString(CryptoJS.enc.Utf8);
//   return JSON.parse(decryptedText);
// }




  return next(req); 
// };







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
}