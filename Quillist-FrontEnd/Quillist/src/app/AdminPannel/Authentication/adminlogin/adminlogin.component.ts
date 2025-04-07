import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserServicesService } from '../../../services/user-services.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adminlogin',
  standalone: false,
  templateUrl: './adminlogin.component.html',
  styleUrl: './adminlogin.component.css',
})
export class AdminloginComponent implements OnInit {
  loginForm: FormGroup;
  email: any;
  password: any;
  loading: boolean = false;
  errmsg: string = '';
  private tokenKey = 'auth-token';

  constructor(
    private fb: FormBuilder,
    private userService: UserServicesService,
    private router: Router
  ) {
    // Initialize loginForm directly in the constructor
    this.loginForm = this.fb.group({
      userName: ['', [Validators.required, Validators.required]],
      password: ['', [Validators.required, Validators.minLength(3)]],
    });
  }


  ngOnInit(): void {
    console.log('admin Data : ' + this.loginForm.value);
  }

  onSubmitUserData() {
    if (this.loginForm.valid) {
      console.log(this.loginForm.value);
      this.loading = true;
    } else {
      console.error('Form is invalid!');
    }

    // const formdata = new FormData();
    // formdata.append('userName', this.loginForm.get('userName')?.value);
    // formdata.append('password', this.loginForm.get('password')?.value);

    // this.userService.login(formdata).subscribe(
    //   (response: any) => {
    //     console.log('login successfull');

    //       console.log('Login response:', response);

    // if (response && response.token) {
    //   console.log('token:', response.token);
    //   // localStorage.setItem('userId', response.userName);
    //   localStorage.setItem('token', response.token);  // Store the token in localStorage
    //   this.router.navigate(['/admin']);  // Navigate to the admin page
    // } else {
    //   console.error('Token not found in the response');
    //   this.errmsg = 'Failed to retrieve token.';
    // }
    //   },
    //   (error) => {
    //     console.log('Login Failed');
    //     this.errmsg = 'Invalid username and password. please try again!!';
    //   },
    //   () => {
    //     // Set loading to false once the request is completed
    //     this.loading = false;
    //   }
    // );







    // ========================new code from here+========================
    
  const loginData = {
    userName: this.loginForm.get('email')?.value,
    password: this.loginForm.get('password')?.value
  };

  this.userService.login(loginData).subscribe(
    (response: any) => {
      console.log('Login successful');
      console.log('Login response:', response);

      if (response && response.token) {
        console.log('token:', response.token);
        localStorage.setItem('token', response.token);  // Store the token in localStorage
        this.router.navigate(['']);  // Navigate to the admin page
      } else {
        console.error('Token not found in the response');
        this.errmsg = 'Failed to retrieve token.';
      }
    },
    (error) => {
      console.log('Login Failed');
      this.errmsg = 'Invalid username and password. Please try again!';
    },
    () => {
      // Set loading to false once the request is completed
      this.loading = false;
    }
  );
  }
  // adminLogin: FormGroup | undefined ;
}
