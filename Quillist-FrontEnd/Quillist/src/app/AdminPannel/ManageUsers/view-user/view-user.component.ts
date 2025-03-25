import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServicesService } from '../../../services/user-services.service';

@Component({
  selector: 'app-view-user',
  standalone: false,
  templateUrl: './view-user.component.html',
  styleUrl: './view-user.component.css',
})
export class ViewUserComponent implements OnInit {
  users: any[] = [];
  page: number = 1;

  constructor(private userService: UserServicesService) {}
  ngOnInit(): void {
    this.userService.getUsers().subscribe({
      next: (data) => {
        console.log('Users fetched:', data); // Debug log
        this.users = data;
      },
      error: (error) => {
        console.error('Error fetching users:', error);
        alert(`Error: ${error.status} - ${error.statusText}`);
      },
    });
  }

  getUsers(): void {
    this.userService.getUsers().subscribe({
      next: (data) => {
        console.log('Users fetched:', data); // Debug log
        this.users = data;
      },
      error: (error) => {
        console.error('Error fetching users:', error);
        alert(`Error: ${error.status} - ${error.statusText}`);
      },
    });
  }

  deleteUser(userId: number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(userId).subscribe({
        next: () => {
          this.users = this.users.filter((user) => user.id !== userId);
          alert('User deleted successfully');
        },
        error: (error) => {
          console.error('Error deleting user:', error);
          alert(`Error: ${error.status} - ${error.statusText}`);
        },
      });
    }
  }
}
