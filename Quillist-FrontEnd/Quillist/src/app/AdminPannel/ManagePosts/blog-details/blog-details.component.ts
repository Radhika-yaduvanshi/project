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
  // views:any; 
averageRating: number = 0;
userId: number = 1; // For demo, use logged-in user from auth later


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

    if (blogId) {
      this.fetchBlogDetails(+blogId);
      this.fetchAverageRating(+blogId); // <-- fetch average rating
    }
    
  }

  selectedRating: number = 0;
hoveredRating: number = 0;
stars = Array(5).fill(0);

setRating(rating: number) {
  this.selectedRating = rating;
  // You can also emit this rating to the backend here
  if(this.blog?.id){
    this.postService.rateBlog(this.userId, this.blog.id, rating).subscribe({
      next: (response) => {
        console.log("Rating submitted!", response);
        this.fetchAverageRating(this.blog.id); // Refresh avg rating
      },
      error: (err) => {
        console.error("Error submitting rating", err);
      }
    })
  }
}
fetchAverageRating(blogId: number): void {
  this.postService.getAverageRating(blogId).subscribe({
    next: (avg) => {
      this.averageRating = avg;
    },
    error: (err) => {
      console.error("Error fetching average rating", err);
    }
  });
}

hoverRating(rating: number) {
  this.hoveredRating = rating;
}

//   fetchBlogDetails(blogId: number): void {
//     this.postService.incrementView(blogId).subscribe(() =>{

      
//       // console.log("blog id in view increment : "+blogId);
      
//     this.postService.getBlogById(blogId).subscribe(
//       (data) => {

        
//         if (data) {
//           // this.views=data.views,
//           console.log("views "+this.views)
//           this.blog = {
//             ...data,
//             views:data.views,
//             safeContent: this.sanitizer.bypassSecurityTrustHtml(data.content), // Sanitize HTML content
//             // console.log("views of blog : "+data.views);
         
//             // console.log("views : "+this.views);
//           };

//           // Check if user_id exists and is valid
//           if (data.userId) {
//             this.fetchUserDetails(data.userId); // Assuming 'user_id' is available in the blog data
//           } else {
//             this.errorMessage = 'Author information is missing for this blog.';
//           }

//           // Store category if needed
//           this.category = data.category;
//         }
//       },
//       (error) => {
//         this.errorMessage =
//           'Failed to load blog details. Please try again later.';
//       }
//     );
//     // this.postService.incrementView(blogId).subscribe();
//   })
// }
fetchBlogDetails(blogId: number): void {
  this.postService.incrementView(blogId).subscribe({
    next: () => {
      console.log("View count incremented for blog ID:", blogId);

      // Now fetch updated blog details
      this.postService.getBlogById(blogId).subscribe({
        next: (data) => {
          if (data) {
            console.log("data : ",data.views);
            
            this.blog = {
              ...data,
              
              safeContent: this.sanitizer.bypassSecurityTrustHtml(data.content),
            };
            if (data.views !== undefined) {
              console.log("✅ View count from backend:", data.views);
            } else {
              console.warn("⚠️ View count is undefined in backend response.");
            }
            this.category = data.category;
            console.log("views data now: "+data);
            

            if (data.userId) {
              this.fetchUserDetails(data.userId);
            } else {
              this.errorMessage = 'Author information is missing for this blog.';
            }

            console.log("Updated view count:", data.views);
          }
        },
        error: () => {
          this.errorMessage = 'Failed to load blog details. Please try again later.';
        },
      });
    },
    error: () => {
      this.errorMessage = 'Failed to increment view count.';
    },
  });
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





  //Ratings 



}
