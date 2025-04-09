import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserServicesService } from '../../../services/user-services.service';

@Component({
  selector: 'app-add-user',
  standalone: false,
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
})
export class AddUserComponent implements OnInit {
  userForm!: FormGroup;
  profilePhoto: File | null = null;

  formBuilder=inject(FormBuilder)
  userService = inject(UserServicesService)

  constructor(
    // private formBuilder: FormBuilder,
    // private userService: UserServicesService
  ) {
    // Initialize the form here
    // this.userForm = this.formBuilder.group({
    //   userName: ['', Validators.required],
    //   email: ['', [Validators.required, Validators.email]],
    //   password: ['', [Validators.required, Validators.minLength(6)]],
    //   role:['',[Validators.required]]
    //   // profilePhoto: [null, Validators.required],
    // });
  }
  ngOnInit() {
    this.userForm = this.formBuilder.group({
      userName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role:['',[Validators.required]]
      // profilePhoto: [null, Validators.required],
    });
  }

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.profilePhoto = file;
      this.userForm.patchValue({
        profilePhoto: this.profilePhoto,
      });
    }
  }

  onSubmitUserData(): void {
    if (this.userForm.invalid) {
      return;
    }
    console.log(this.userForm.value);
    const userData = {
      userName: this.userForm.value.userName,
      email: this.userForm.value.email,
      password: this.userForm.value.password,
      role:this.userForm.value.role
      // profilePhoto:this.userForm.value.profilePhoto
    };


    

    this.userService.registerUser(userData).subscribe({
      next: (response) => {
        
        console.log('User registered successfully', response);
        alert('User registered successfully');
        console.log(this.userForm.value);
        console.log("Response from user data : "+response)
        
      },
      error: (error) => {
        console.error('Error registering user', error);
        alert('Error registering user');
      },
    });
  }
}
