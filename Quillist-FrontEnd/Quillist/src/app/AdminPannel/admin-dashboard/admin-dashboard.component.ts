import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  standalone: false,
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
  totalUsers = 0;
  totalCategories = 0;
  totalBlogs = 0;

  ngOnInit(): void {
    // Simulated data — replace with real service calls
    this.totalUsers = 120;
    this.totalCategories = 10;
    this.totalBlogs = 58;
  }
}
