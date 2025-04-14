import { Component } from '@angular/core';
import { PostServiceService } from '../../../services/post-service.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-view-post',
  standalone: false,
  templateUrl: './view-post.component.html',
  styleUrl: './view-post.component.css',
})
export class ViewPostComponent {
  blogs: any[] = [];
  filteredBlogs: any[] = [];
  searchQuery: string = '';
  errorMessage = '';
  currentPage: number = 1;
  postsPerPage: number = 5;
mainImage: any;

  constructor(
    private blogService: PostServiceService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.fetchBlogs();
    // this.blogService.getTitleImage(blogId).subscribe(blog => {
    //   this.blog = blog;
    //   this.mainImage = blog.images?.find(img => img.isMain); // this line
    // });

  }

  fetchBlogs(): void {
    this.blogService.getAllBlogs().subscribe(
      // (data) => {
      //   this.blogs = data;
      //   this.filteredBlogs = data.map((blog) => ({
      //     ...blog,
      //     safeContent: this.sanitizer.bypassSecurityTrustHtml(blog.content),
      //     showFullContent: false, // Initially, only show the truncated version
      //     mainImage: 'fallback.jpg'

          

      //   }));
      // },
      (data) => {
        this.blogs = data;
  
        this.filteredBlogs = data.map((blog) => {
          const enhancedBlog = {
            ...blog,
            safeContent: this.sanitizer.bypassSecurityTrustHtml(blog.content),
            showFullContent: false,
            mainImage: 'fallback.jpg' // default fallback until image loads
          };
  
          // Fetch the title image for this blog
          this.blogService.getTitleImage(blog.id).subscribe((image) => {
            if (image?.imageData) {
              enhancedBlog.mainImage = 'data:image/jpeg;base64,' + image.imageData;
            }
          });
  
          return enhancedBlog;
        });
      },
      
      (error) => {
        this.errorMessage = 'Failed to load blogs. Please try again later.';
      }
    );
  }


  filterBlogs() {
    this.filteredBlogs = this.blogs.filter(blog => 
      blog.title.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
    this.currentPage = 1;  // Reset to the first page after filtering
    // this.applyPagination();
  }




  deleteBlog(blogId: number, index: number): void {
    if (confirm('Are you sure you want to delete this blog?')) {
      this.blogService.deleteBlog(blogId).subscribe(
        () => {
          this.blogs.splice(index, 1);
          this.filterBlogs(); // Update filtered list
        },
        (error) => {
          alert('Failed to delete blog. Please try again.');
        }
      );
    }
  }

    // Toggle the showFullContent flag when clicking "Read More" or "Show Less"
    toggleContent(blog: any): void {
      blog.showFullContent = !blog.showFullContent;
    }

    getPreviewContent(content: string): string {
      // Get the first 200 characters (adjust as needed)
      return content.length > 200 ? content.substring(0, 200) + '...' : content;
    }
    
}
