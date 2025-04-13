import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserServicesService } from '../../../services/user-services.service';
import { response } from 'express';

@Component({
  selector: 'app-forgot-password',
  standalone: false,
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css',
})
export class ForgotPasswordComponent {
  // forgotPasswordForm = FormGroup;
  fb = inject(FormBuilder);
  userService = inject(UserServicesService);
  showOtpSection = false;
  enteredOtp = '';

  forgotPasswordForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    otp: ['', [Validators.required]],
  });
  onSubmit() {
    if (this.forgotPasswordForm.valid) {
      const email = this.forgotPasswordForm.value.email!;
      console.log('email is : ' + email);

      //send otp now

      this.userService.sendOTP(email).subscribe({
        next: (response) => {
          console.log('OTP sent Sucessfully ...', response);
          // alert('OTP has been sent to your email.');
          this.showOtpSection = true;
        },
        error: (error) => {
          console.error('Error sending OTP:', error);
          // Optionally show error message to user
          alert('Failed to send OTP. Please try again.');
        },
      });
    }
  }
  verifyOtp() {
    const email = this.forgotPasswordForm.value.email!;
    const otp = this.forgotPasswordForm.value.otp;
    this.userService.verifyOtp(email, this.enteredOtp).subscribe({
      next: () => {
        alert('OTP verified successfully!');
      },
      error: (error) => {
        console.error('Error verifying OTP:', error);

        alert('Invalid OTP, try again!');
      },
    });
  }
}
