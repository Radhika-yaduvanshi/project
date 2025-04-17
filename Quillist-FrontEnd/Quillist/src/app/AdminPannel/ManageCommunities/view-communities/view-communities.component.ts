import { Component, inject, OnInit } from '@angular/core';
import { CommunityServiceService } from '../../../services/community-service.service';

@Component({
  selector: 'app-view-communities',
  standalone: false,
  templateUrl: './view-communities.component.html',
  styleUrl: './view-communities.component.css'
})
export class ViewCommunitiesComponent implements OnInit {

private communityService= inject(CommunityServiceService)

communities:any;
successMessage!:'';


  ngOnInit(): void {
    this.loadCommunities();
  }


  loadCommunities(): void {
    this.communityService.getAllCommunities().subscribe({
      next: (communities) => {
        this.communities = communities;  // Store communities
        console.log('All communities:', this.communities);
      },
      error: (err) => {
        console.error('Error fetching communities:', err);
      }
    });
  }
}
