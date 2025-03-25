import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { PostServiceService } from '../../../services/post-service.service';

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
  userId = 1; // Assuming this is coming from a logged-in user
  submissionSuccess: boolean = false; // Track submission success
  submissionError: boolean = false; // Track submission error

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
          reader.onload = function (e: any) {
            callback(e.target.result, { alt: file.name });
          };
          reader.readAsDataURL(file); // Convert the file to base64
        }
      });
      input.click(); // Trigger the file input dialog
    }
  }

  constructor(
    private fb: FormBuilder,
    private blogService: PostServiceService
  ) {}

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories() {
    // Fetch categories from your backend if needed
    this.blogService.getCategories().subscribe((categories: any[]) => {
      this.categories = categories;
    });
  }

  onSubmit(blogForm: any): void {
    if (blogForm.valid) {
      const blogData = {
        title: this.postTitle,
        content: this.postContent,
        category: {
          id: this.selectedCategory, // Send only the category ID here
        },
        user_id: this.userId, // Assuming userId is 1
        blogstatus: 'Approved', // Make sure the status is an enum value
      };
      this.blogService.createBlog(blogData, this.userId).subscribe(
        (response) => {
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
  }
}
