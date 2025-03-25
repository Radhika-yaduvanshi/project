import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router'; // ✅ Correct import
import { UserServicesService } from '../../../services/user-services.service';

@Component({
  selector: 'app-add-user',
  standalone: false,
  templateUrl: './add-user.component.html',
  styleUrl: './add-user.component.css',
})
export class AddUserComponent {
  constructor(private userService: UserServicesService) {}
 
  user = {
    id:1,
    name: '',
    email: '',
    password: '',
    profilePhoto: null as File | null,
  };
  profilePhoto: File | null = null; // Initialize properly

  // // onFileSelected(event: Event) {
  // //   const input = event.target as HTMLInputElement;
  // //   if (input.files && input.files.length > 0) {
  // //     this.user.profilePhoto = input.files[0]; // Store file
  // //   }
  // // }

  // onFileChange(event: any): void {
  //   const file = event.target.files[0];
  //   if (file) {
  //     this.profilePhoto = file;

  //   }
  // }

  // onSubmitUserData(formData: any) {
  //   if (!this.profilePhoto) {
  //     console.log('No file selected');
  //     return;
  //   }
  //       const formDataObj = new FormData();
  //       formDataObj.append('name', this.user.name);
  //       formDataObj.append('email', this.user.email);
  //       formDataObj.append('password', this.user.password);
  //       // formDataObj.append('profilePhoto', this.profilePhoto, this.profilePhoto.name);

  //           // Check if profilePhoto is not null and append it to FormData
  //           // formDataObj.append('id', this.user.id); // Add user ID if needed
  //   if (this.profilePhoto) {
  //     // formDataObj.append('profilePhoto', this.profilePhoto, this.profilePhoto.name);
  //     formDataObj.append('profilePhoto',  this.profilePhoto.name);
      

  //   }
  //   console.log('Form Submitted!', formDataObj); 

  //   // Simulate form submission (Replace this with actual API call)
  //   // this.userService.uploadProfilePhoto(this.profilePhoto, this.user.id).subscribe({
  //   //   next: (response) => {
  //   //     console.log('User registered successfully', response);
  //   //     alert('User registered successfully');
  //   //   },
    
  //       // // After uploading the photo, proceed with user registration
  //       this.userService.registerUser(formDataObj).subscribe({
  //         next: (response) => {
  //           console.log('User registered successfully', response);
  //           alert('User registered successfully');
  //         },
  //         error: (error) => {
  //           console.error('Error registering user', error);
  //           alert('Error registering user');
  //         },
  //       });
  //     }













  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.profilePhoto = file; // Assign the file to profilePhoto
      console.log('Selected file:', file);
    }
  }
  



      // new code from here

      // Updated onSubmitUserData method to not send user id
      onSubmitUserData(formData: any) {
        if (!this.user.name || !this.user.email || !this.user.password) {
          console.log('Please fill all fields');
          return;
        }

  // Create FormData to send both the user details and the profile photo
  const formDataObj = new FormData();
  formDataObj.append('name', this.user.name);
  formDataObj.append('email', this.user.email);
  formDataObj.append('password', this.user.password);

  if (this.profilePhoto) {
    formDataObj.append('profilePhoto', this.profilePhoto, this.profilePhoto.name); // Append the selected file
  }

  // Call the service method to register the user with image
  this.userService.registerUser(formDataObj).subscribe({
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
