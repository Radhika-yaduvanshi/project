import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PostServiceService {
  constructor(private http: HttpClient) {}

  private apiUrl = '/blog';

  private caturl = '/category';

  createBlog(blogData: any, userId: number) {
    return this.http.post(`${this.apiUrl}/save/${userId}`, blogData, {
      responseType: 'text',
    });
  }

  // Get all blogs
  getAllBlogs(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/getAllBlogs`);
  }

  // Search for blogs by title
  searchBlogByTitle(title: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/searchBlogByTitle/${title}`);
  }
  deleteBlog(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, {
      responseType: 'text',
    });
  }

  // Add a comment to a blog
  addCommentToBlog(blogId: number, comment: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/AddComment/${blogId}`, comment);
  }

  // Get comments by blog ID
  getCommentsByBlogId(blogId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/getCommentsByBlogId/${blogId}`);
  }

  // Get a blog by ID
  getBlogById(blogId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/getBlogById/${blogId}`);
  }

  // Search blog by title and category
  searchBlogByTitleAndCategory(blog: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/searchBlogByTitleAndCategory`, blog);
  }

  // Get user by ID
  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/getUserById/${id}`);
  }

  getCategories() {
    return this.http.get<any[]>(`${this.caturl}/getAllCategory`);
  }
}
