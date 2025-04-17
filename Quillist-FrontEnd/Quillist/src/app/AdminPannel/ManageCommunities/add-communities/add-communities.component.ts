import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserServicesService } from '../../../services/user-services.service';
import { PostServiceService } from '../../../services/post-service.service';
import { CommunityServiceService } from '../../../services/community-service.service';

@Component({
  selector: 'app-add-communities',
  standalone: false,
  templateUrl: './add-communities.component.html',
  styleUrl: './add-communities.component.css',
})
export class AddCommunitiesComponent implements OnInit {
  successMessage: string = '';
  userId: number | null = null;
  communityForm!: FormGroup<any>;

  userService = inject(UserServicesService);
  postService = inject(PostServiceService);
  communityService = inject(CommunityServiceService);
  fb = inject(FormBuilder);

  constructor() {}

  ngOnInit(): void {
    this.communityForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      isActive: [true],
    });

    // this.addCommunity();
  }

  addCommunity() {
    const userId = this.userService.getUserIdFromToken().subscribe({
      next: (userId) => {
        this.userId = userId;
        console.log('user id in add community ' + userId);
        if (this.communityForm.invalid) {
          return;
        }
        console.log(
          'here outside community data ' + userId + this.communityForm
        );
        const formData = this.communityForm.value;
        console.log("form data is : "+formData);
        
        this.communityService
          .createCommunity(userId, formData)
          .subscribe({
            next: (communityData) => {
              console.log('Community created successfully:', formData);
              this.successMessage = 'Community created successfully!';
              this.communityForm.reset(); // optional
              console.log('community data : ' + formData.value);
              this.communityForm.reset();
            },
          });
      },

      error: (err) => {
        console.log('User id not found .... ');
      },
    });
  }
}
