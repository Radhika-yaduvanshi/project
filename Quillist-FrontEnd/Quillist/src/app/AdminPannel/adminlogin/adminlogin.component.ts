import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';



@Component({
  selector: 'app-adminlogin',
  standalone: false,
  templateUrl: './adminlogin.component.html',
  styleUrl: './adminlogin.component.css'
})
export class AdminloginComponent implements OnInit{
  loginForm: FormGroup ;
email: any;
password: any;


  constructor(private fb: FormBuilder) {
    // Initialize loginForm directly in the constructor
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });

   }


  ngOnInit(): void {


    console.log("admin Data : "+this.loginForm.value);
    
  }

  onSubmitUserData() {
    if (this.loginForm.valid) {
      console.log(this.loginForm.value);
    } else {
      console.error('Form is invalid!');
    }
  }
adminLogin: FormGroup | undefined ;

}
