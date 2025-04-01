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
    name: '',
    email: '',
    password: '',
    profilePhoto: null as File | null,
  };
  profilePhoto: File | null = null; // Initialize properly

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.user.profilePhoto = input.files[0]; // Store file
    }
  }

  onSubmitUserData(formData: any) {
    if (!this.user.profilePhoto) {
      console.log('No file selected');
      return;
    }

    const formDataObj = new FormData();
    formDataObj.append('name', this.user.name);
    formDataObj.append('email', this.user.email);
    formDataObj.append('password', this.user.password);
    // formDataObj.append('profilePhoto', this.user.profilePhoto);
    if (this.user.profilePhoto) {
      formData.append('profilePhoto', this.user.profilePhoto);
    } else {
      console.log('No file selected');
      return;
    }

    // Simulate form submission (Replace this with actual API call)

    this.userService.registerUser(formData).subscribe({
      next: (response) => {
        console.log('User registered successfully', response);
        alert('User registered successfully');
      },
      error: (error) => {
        console.error('Error registering user', error);
        alert('Error registering user');
      },
    });
    console.log('Form Submitted!', formData);
  }
}
