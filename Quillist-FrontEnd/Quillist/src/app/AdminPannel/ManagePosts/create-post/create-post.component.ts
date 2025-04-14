import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { PostServiceService } from '../../../services/post-service.service';
import { UserServicesService } from '../../../services/user-services.service';

interface Category {
  id: number;
  categoryName: string; // Change this to match the API response
}
@Component({
  selector: 'app-create-post',
  standalone: false,
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.css',
})
export class CreatePostComponent implements OnInit {
  postTitle: string = '';
  postContent: string = ''; // Store content from TinyMCE editor
  selectedCategory: number | string = ''; // Selected category ID
  categories: Category[] = []; // Categories array
  email: string = ' '; // Assuming this is coming from a logged-in user
  userId: any;
  submissionSuccess: boolean = false; // Track submission success
  submissionError: boolean = false; // Track submission error
  firstImage: string | null = null; 

  

  // public postContent: string = '';
  // submitPost() {
  //   console.log('Post Content:', this.postContent);
  //   alert('Post submitted successfully!');
  // }

  // postContent: string = ''; // ngModel for the editor content

  // File picker callback function
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
          reader.onload =  (e: any) =>{//here removed function keyword before (e:any)
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

  constructor(
    private fb: FormBuilder,
    private blogService: PostServiceService,
    private userService: UserServicesService
  ) {}

   ngOnInit():void {
    this.getCategories();
    this.userService.getUserIdFromToken().subscribe({
      next: (id) => {
        this.userId = id;  // Set userId when the observable emits the value
        console.log('User ID in ngOnInit: ' + this.userId);
      },
      error: (err) => {
        console.error('Error fetching User ID', err);
      }
    });

    if (!this.email) {
      console.error('User is not logged in or token is invalid');
    }
  }

  getCategories() {
    // Fetch categories from your backend if needed
    this.blogService.getCategories().subscribe((categories: any[]) => {
      this.categories = categories;
    });
  }

  onSubmit(blogForm: any): void {


    if(this.userId){
      console.log("USer id is in onsubmit method : "+this.userId);
    if (blogForm.valid) {
      // const userId = this.userId;
      const blogData = {
        title: this.postTitle,
        content: this.postContent,
        category: {
          id: this.selectedCategory, // Send only the category ID here
        },
        user_id:this.userId, // Assuming userId is 1
        titleImage: this.firstImage,
        // blogstatus: 'Approved', // Make sure the status is an enum value
      };
      this.blogService.createBlog(blogData,this.userId).subscribe(
        (response) => {
          console.log(this.email + 'This is the emial here in component');
          console.log('Blog created successfully:', response);
          this.submissionSuccess = true; // Set success flag
          this.submissionError = false; // Reset error flag
        },
        (error) => {
          console.error('Error creating blog:', error);
          this.submissionError = true; // Set error flag
          this.submissionSuccess = false; // Reset success flag
        }
      );
    }
  }else {
    console.error('User ID is not available');
    }
  }
}
