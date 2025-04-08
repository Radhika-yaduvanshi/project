import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserServicesService } from '../../../services/user-services.service';

@Component({
  selector: 'app-add-user',
  standalone: false,
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
})
export class AddUserComponent implements OnInit {
  userForm: FormGroup;
  profilePhoto: File | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserServicesService
  ) {
    // Initialize the form here
    this.userForm = this.formBuilder.group({
      userName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      // profilePhoto: [null, Validators.required],
    });
  }
  ngOnInit() {
    // this.userForm = this.formBuilder.group({
    //   name: ['', Validators.required],
    //   email: ['', [Validators.required, Validators.email]],
    //   password: ['', [Validators.required, Validators.minLength(6)]],
    //   profilePhoto: [null, Validators.required],
    // });
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
    const userData = {
      userName: this.userForm.value.userName,
      email: this.userForm.value.email,
      password: this.userForm.value.password,
      // profilePhoto:this.userForm.value.profilePhoto
    };

    // const formData = new FormData();
    // formData.append('name', this.userForm.value.userName);
    // formData.append('email', this.userForm.value.email);
    // formData.append('password', this.userForm.value.password);

    // if (this.profilePhoto) {
    //   formData.append('profilePhoto', this.profilePhoto, this.profilePhoto.name);
    // }
      // Create a JSON object for the user data


    // console.log('Form Submitted!', formData);
    // formData.append('user', JSON.stringify(userData));
    

    this.userService.registerUser(userData).subscribe({
      next: (response) => {
        
        console.log('User registered successfully', response);
        alert('User registered successfully');
      },
      error: (error) => {
        console.error('Error registering user', error);
        alert('Error registering user');
      },
    });
  }
}
