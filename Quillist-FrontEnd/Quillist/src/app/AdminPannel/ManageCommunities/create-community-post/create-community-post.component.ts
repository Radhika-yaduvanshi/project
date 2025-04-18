import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { UserServicesService } from '../../../services/user-services.service';
import { PostServiceService } from '../../../services/post-service.service';
import { CommunityServiceService } from '../../../services/community-service.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-create-community-post',
  standalone: false,
  templateUrl: './create-community-post.component.html',
  styleUrl: './create-community-post.component.css',
})
export class CreateCommunityPostComponent {
  postTitle: string = '';
  postContent: string = ''; // Store content from TinyMCE editor
  selectedCategory: number | string = ''; // Selected category ID
  // categories: Category[] = []; // Categories array
  email: string = ' '; // Assuming this is coming from a logged-in user
  userId: any;
  communityId: any;
  submissionSuccess: boolean = false; // Track submission success
  submissionError: boolean = false; // Track submission error
  firstImage: string | null = null;

  // blogData: any = {
  //   title: '',
  //   content: ''
  // };

  constructor(
    private fb: FormBuilder,
    private blogService: PostServiceService,
    private userService: UserServicesService,
    private communityService: CommunityServiceService,
    private rout: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // this.getCategories();
    this.userService.getUserIdFromToken().subscribe({
      next: (id) => {
        this.userId = id; // Set userId when the observable emits the value
        console.log('User ID in ngOnInit in create community: ' + this.userId);
      },
      error: (err) => {
        console.error('Error fetching User ID', err);
      },
    });

    if (!this.email) {
      console.error('User is not logged in or token is invalid');
    }
    this.communityId = Number(this.rout.snapshot.paramMap.get('communityId'));
  }

  // getCategories() {
  //   // Fetch categories from your backend if needed
  //   this.blogService.getCategories().subscribe((categories: any[]) => {
  //     this.categories = categories;
  //   });
  // }

  // Ensure this component is getting the correct values for userId and communityId
  onSubmit(formData: any): void {
    const userId = this.userId; // Get the logged-in user's ID
    const communityId = this.communityId; // Get the community ID from the route

    if (formData.valid) {
      // Construct the community data (title, content, etc.)
      const communityData = {
        title: this.postTitle,
        content: this.postContent,
        // Include any other necessary data like image or status
      };

      console.log('userId:', userId);
      console.log('communityId:', communityId);
      console.log('communityData:', communityData);

      // Call the createCommunity method with the correct arguments
      this.communityService
        .createCommunityPost(userId, communityId, communityData)
        .subscribe({
          next: (response) => {
            console.log('Community created successfully:', response);
            this.router.navigate(['/community-detail', communityId]); // Navigate to the community detail page after creation
          },
          error: (err) => {
            console.error('Error creating community:', err);
          },
        });
    }
  }

  filePickerCallback(callback: any, value: any, meta: any) {
    if (meta.filetype === 'image') {
      // Open file browser to select an image (this will trigger a file input dialog)
      const input = document.createElement('input');
      input.setAttribute('type', 'file');
      input.setAttribute('accept', 'image/*');
      input.addEventListener('change', (event: any) => {
        const file = event.target.files[0];

        // Ensure the file is an image
        if (file && file.type.startsWith('image/')) {
          const reader = new FileReader();
          reader.onload = (e: any) => {
            //here removed function keyword before (e:any)
            callback(e.target.result, { alt: file.name });

            if (!this.firstImage) {
              this.firstImage = e.target.result; // Store the base64 data of the image
            }
          };
          reader.readAsDataURL(file); // Convert the file to base64
        }
      });
      input.click(); // Trigger the file input dialog
    }
  }
}
