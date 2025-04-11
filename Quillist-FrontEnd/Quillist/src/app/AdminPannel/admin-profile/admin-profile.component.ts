import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostServiceService } from '../../services/post-service.service';
import { UserServicesService } from '../../services/user-services.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-admin-profile',
  standalone: false,
  templateUrl: './admin-profile.component.html',
  styleUrl: './admin-profile.component.css'
})
export class AdminProfileComponent implements OnInit  {
  blog: any;
  authorId: any; // To store user details
  category: any = null; // To store category details
  errorMessage: string = '';
  author:any;
  blogs:any;
  private userService=inject(UserServicesService);
  private blogService=inject(PostServiceService);
  private domSanitizer=inject(DomSanitizer);
  email: any;

  deleteMsg:string="";




  sanitizeHTML(html:string):SafeHtml{
    return this.domSanitizer.bypassSecurityTrustHtml(html);

  }

ngOnInit() {
  this.userService.getUserIdFromToken().subscribe({
    next: (id) => {
      this.authorId = id; // Set the userId to the component variable
      console.log('User ID from token:', this.authorId);



      this.userService.getUserById(this.authorId).subscribe({
        next:(user)=>{
          this.author=user;
          console.log("User is : "+user.userName);  
        } 
      });
      // this.blogService.getBlogByUserId(this.authorId).subscribe({
      //   next:(blogs)=>{
      //     this.blogs=blogs;
      //     console.log("User id is ; "+this.authorId);
      //     console.log("Blog is : "+blogs);
          
          
      //   blogs.forEach(blog=>console.log("blog title is : "+blog.title))          
      //   }
      // })

      this.blogService.getBlogByUserId(this.authorId).subscribe({
        next: (blogs) => {
          this.blogs = blogs.map((b: any) => ({
            ...b,
            safeContent: this.domSanitizer.bypassSecurityTrustHtml(b.content),
            showFullContent: false
          }));
        }
      });
  


    },
    error: (err) => {
      console.error('Error fetching user ID:', err);
    }
    
  });  
}
toggleContent(blog: any): void {
  blog.showFullContent = !blog.showFullContent;
}

getPreviewContent(content: string): string {
  //  const plainText = content.replace(/<[^>]+>/g, ''); 
  // Get the first 200 characters (adjust as needed)
  return content.length > 200 ? content.substring(0, 200) + '...' : content;
}


deleteBlog(id:number):any{
  this.blogService.deleteBlog(id).subscribe({

    
   next: (blog)=>{
      this.deleteMsg="Blog Deleted Successfully";
      console.log("blog deleted ");
            // Remove deleted blog from the list (UI update)
            this.blogs = this.blogs.filter((b: any) => b.id !== id);
      
    }
    
  })

}


}
