import { Component } from '@angular/core';
import { PostServiceService } from '../../../services/post-service.service';
// import { Category } from '../manage-categories/manage-categories.component';

export interface Category {
  id: number;
  categoryName: string;
  // description: string;
}
@Component({
  selector: 'app-add-categories',
  standalone: false,
  templateUrl: './add-categories.component.html',
  styleUrl: './add-categories.component.css',
})
export class AddCategoriesComponent {
  category: Category = {
    categoryName: '',
    id: 0,
  };
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private categoryService: PostServiceService) {}

  addCategory(categorydata: any): void {
    this.categoryService.addcategories(categorydata).subscribe(
      (response) => {
        this.successMessage = 'Category added successfully!';
        this.errorMessage = ''; // Clear any previous error messages
      },
      (error) => {
        this.errorMessage = 'Failed to add category. Please try again.';
        this.successMessage = ''; // Clear success message
      }
    );
  }
}
