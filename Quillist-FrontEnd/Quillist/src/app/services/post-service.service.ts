import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../AdminPannel/ManageCategories/add-categories/add-categories.component';
import { response } from 'express';

@Injectable({
  providedIn: 'root',
})
export class PostServiceService {
  constructor(private http: HttpClient) {}

  private apiUrl = '/blog';

  private caturl = '/category';

  private ratings = '/ratings';  

  createBlog(blogData: any, userId: number) {
    return this.http.post(`${this.apiUrl}/save/${userId}`, blogData, {
      responseType: 'text',
    });
  }
  // createBlog(blogData: FormData) {
  //   return this.http.post(`${this.apiUrl}/create`, blogData, {
  //     responseType: 'text',
  //   });
  // }

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

  // all about categories

  // Get all categories
  getCategories() {
    return this.http.get<any[]>(`${this.caturl}/getAllCategory`);
  }

  addcategories(category: Category) {
    return this.http.post(`${this.caturl}/add`, category, {
      responseType: 'text',
    });
  }

  // Delete a category by ID
  deleteCategory(id: number): Observable<any> {
    return this.http.delete(`${this.caturl}/delete/${id}`, {
      responseType: 'text',
    });
  }

  // Update a category by ID
  updateCategory(id: number, category: Category): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${id}`, category, {
      responseType: 'text',
    });
  }


  getBlogByUserId(id:number):Observable<any[]>{
    return this.http.get<any[]>(`${this.apiUrl}/getBlogByUserId/${id}`,{
      responseType:'json',
    });
  }


  //get title image 

  getTitleImage(blogId:number):Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/title-image/${blogId}`)
  }


  incrementView(blogId: number) {
    return this.http.put(`${this.apiUrl}/${blogId}/view`,{});
  }

  mostViewdBlogs():any{
    return this.http.get(`${this.apiUrl}/most-viewed-blogs`,{responseType:'json'})
  }
  




  //ratigns 

  rateBlog(userId: number, blogId: number, ratingValue: number): Observable<any> {
    // Using the proxy URL for ratings
    const url = `${this.ratings}?userId=${userId}&blogId=${blogId}&ratingValue=${ratingValue}`;
    return this.http.post(url, {});  // POST body is empty because params are in URL
  }

  getAverageRating(blogId: number): Observable<number> {
    return this.http.get<number>(`${this.ratings}/average/${blogId}`);
  }

  getRatingsForBlog(blogId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.ratings}/blog/${blogId}`);
  }
}
