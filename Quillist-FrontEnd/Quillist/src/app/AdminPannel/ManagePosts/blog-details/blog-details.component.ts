import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostServiceService } from '../../../services/post-service.service';
import { UserServicesService } from '../../../services/user-services.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-blog-details',
  standalone: false,
  templateUrl: './blog-details.component.html',
  styleUrls: ['./blog-details.component.css'],
})
export class BlogDetailsComponent implements OnInit {
  blog: any = null;
  author: any = null; // To store user details
  category: any = null; // To store category details
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private postService: PostServiceService,
    private userService: UserServicesService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    const blogId = this.route.snapshot.paramMap.get('id');
    console.log('bllog id is : in blog-detail page : ' + blogId);

    if (blogId) {
      this.fetchBlogDetails(+blogId);
    }
  }

  fetchBlogDetails(blogId: number): void {
    this.postService.getBlogById(blogId).subscribe(
      (data) => {
        if (data) {
          this.blog = {
            ...data,
            safeContent: this.sanitizer.bypassSecurityTrustHtml(data.content), // Sanitize HTML content
          };

          // Check if user_id exists and is valid
          if (data.userId) {
            this.fetchUserDetails(data.userId); // Assuming 'user_id' is available in the blog data
          } else {
            this.errorMessage = 'Author information is missing for this blog.';
          }

          // Store category if needed
          this.category = data.category;
        }
      },
      (error) => {
        this.errorMessage =
          'Failed to load blog details. Please try again later.';
      }
    );
  }

  fetchUserDetails(userId: number): void {
    this.userService.getUserById(userId).subscribe(
      (user) => {
        if (user) {
          this.author = user;
          console.log('user id is : ' + userId);
        } else {
          this.errorMessage = 'Failed to load author details.';
        }
      },
      (error) => {
        console.error('Error fetching user details:', error);
        this.errorMessage = 'Failed to load author information.';
      }
    );
  }
}
