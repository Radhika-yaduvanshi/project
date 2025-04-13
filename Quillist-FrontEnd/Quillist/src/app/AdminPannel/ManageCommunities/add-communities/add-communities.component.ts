import { Component } from '@angular/core';

@Component({
  selector: 'app-add-communities',
  standalone: false,
  templateUrl: './add-communities.component.html',
  styleUrl: './add-communities.component.css',
})
export class AddCommunitiesComponent {
  newCommunity = {
    name: 'Tech Innovators',
    description:
      'A place where developers and tech enthusiasts collaborate to share innovative ideas, tutorials, and tech news.',
  };

  createCommunity() {
    console.log('Community Created:', this.newCommunity);
    // TODO: Send this to backend (via service)
    alert(`Community "${this.newCommunity.name}" created successfully!`);
    // Reset form
    this.newCommunity = {
      name: '',
      description: '',
    };
  }
}
