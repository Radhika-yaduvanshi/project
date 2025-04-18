import { Component, inject, OnInit } from '@angular/core';
import { UserServicesService } from '../../../services/user-services.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommunityServiceService } from '../../../services/community-service.service';

@Component({
  selector: 'app-community-dashboard',
  standalone: false,
  templateUrl: './community-dashboard.component.html',
  styleUrl: './community-dashboard.component.css'
})
export class CommunityDashboardComponent implements OnInit{



  communities: any[] = [];
  userId: number | null = null;
  communityId!: number;

  route=inject(ActivatedRoute)
  
  userService=inject(UserServicesService)
  router = inject(Router)
  communityService=inject(CommunityServiceService)

  

  ngOnInit(): void {
    this.communityId = Number(this.route.snapshot.paramMap.get('id'));

    console.log("Community id in ng oninit: "+this.communityId);
    
    this.loadUserCommunities();
  }

  viewCommunityPosts(communityId: number) {
    this.router.navigate(['/manage-community/community-detail',communityId]);
    }

    // getCommunitiesById(communityId:number):any{
    //   this.communityService.getCommunityById(communityId).subscribe({
    //     next:(communityId)=>{
    //       this.communityId=communityId;
    //       console.log("community id is : "+this.communityId);
          
    //     }
    //   })
    // }

  loadUserCommunities():void{
    this.userService.getUserIdFromToken().subscribe({
      next:(userId)=>{
        this.userId=userId;
        console.log("user Id  is : "+this.userId);

        this.communityService.getCommunitiesByUserId(userId).subscribe({
          next:(communities)=>{
            this.communities=communities;
            console.log("communities : "+this.communities);
            
          },
          error:(err)=>{
            console.error('Error fetching communities:', err);
          }
        })
        
      },
      error: (err) => {
        console.error('Error fetching user ID:', err);},
      
    })
  }
}
