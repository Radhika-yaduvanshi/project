import { Component } from '@angular/core';
import { PostServiceService } from '../../../services/post-service.service';
import { DomSanitizer } from '@angular/platform-browser';
import { CommunityServiceService } from '../../../services/community-service.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-community-detail',
  standalone: false,
  templateUrl: './community-detail.component.html',
  styleUrl: './community-detail.component.css'
})
export class CommunityDetailComponent {
  blogs: any[] = [];
  filteredBlogs: any[] = [];
  searchQuery: string = '';
  errorMessage = '';
  currentPage: number = 1;
  postsPerPage: number = 5;
mainImage: any;
communityId!:number;

  ratings: any[] = [];
  averageRating: number = 0;

  constructor(
    // private blogService: PostServiceService,
    private sanitizer: DomSanitizer,
    private communityService:CommunityServiceService,
    private route:ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.communityId = Number(this.route.snapshot.paramMap.get('id')); 
    console.log("Community ID in detail component:", this.communityId);
    // 👈 Get communityId from route

    this.fetchBlogs();
    // this.blogService.getTitleImage(blogId).subscribe(blog => {
    //   this.blog = blog;
    //   this.mainImage = blog.images?.find(img => img.isMain); // this line
    // });

  }

  fetchBlogs(): void {
    this.communityService.getCommunityPostByCommunityId(this.communityId).subscribe({
      next:(communityPosts)=>{
        this.blogs=communityPosts;
        console.log("Community posts : "+this.blogs);
        this.filteredBlogs = communityPosts.map((blog) => ({
          ...blog,
          safeContent: this.sanitizer.bypassSecurityTrustHtml(blog.content),
          showFullContent: false,
          mainImage: 'fallback.jpg'
        }));
      },
      error: (err) => {
        this.errorMessage = 'Failed to load blogs. Please try again later.';
        console.error('Error fetching posts:', err);
      }
    })
  }


  filterBlogs() {
    this.filteredBlogs = this.blogs.filter(blog => 
      blog.title.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
    this.currentPage = 1;  // Reset to the first page after filtering
    // this.applyPagination();
  }




  deleteBlog(blogId: number, index: number): void {
    if (confirm('Are you sure you want to delete this blog?'))
       {
    //   this.blogService.deleteBlog(blogId).subscribe(
    //     () => {
    //       this.blogs.splice(index, 1);
    //       this.filterBlogs(); // Update filtered list
    //     },
    //     (error) => {
    //       alert('Failed to delete blog. Please try again.');
    //     }
    //   );
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
