import { inject, Injectable } from '@angular/core';
import { UserServicesService } from './user-services.service';
import { PostServiceService } from './post-service.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommunityServiceService {

  userService = inject(UserServicesService)
  postService = inject(PostServiceService)


  constructor(private http: HttpClient) { }

      private apiUrl='/community'

   // Create a new community
   createCommunity(userId: number, communityData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/cratecommunity/${userId}`, communityData);
  }

  // Get all communities
  getAllCommunities(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/getallcommunity`);
  }

  // Get a community by its title
  getCommunityByTitle(title: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/getbytitle/${title}`);
  }

  // Get a community by its ID
  getCommunityById(communityId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/getbyid/${communityId}`);
  }

  // Update a community by ID
  updateCommunity(communityId: number, communityData: any): Observable<string> {
    return this.http.put<string>(`${this.apiUrl}/updatecommunity/${communityId}`, communityData);
  }

  // Delete a community by ID
  deleteCommunity(communityId: number): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/deletecommunity/${communityId}`);
  }

  // Get all communities for a specific user
  getCommunitiesByUserId(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/getcommunitybyuserid/${userId}`);
  }


}
