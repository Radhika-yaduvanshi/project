import { Component } from '@angular/core';
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';

@Component({
  selector: 'app-create-post',
  standalone: false,
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.css',
})
export class CreatePostComponent {
  public postContent: string = '';

  submitPost() {
    console.log('Post Content:', this.postContent);
    alert('Post submitted successfully!');
  }
}
