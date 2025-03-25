import { Component } from '@angular/core';
import { PostServiceService } from '../../../services/post-service.service';
import { Category } from '../add-categories/add-categories.component';

@Component({
  selector: 'app-view-categories',
  standalone: false,
  templateUrl: './view-categories.component.html',
  styleUrl: './view-categories.component.css'
})
export class ViewCategoriesComponent {
  categories: Category[] = [];  // Store all categories
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private categoryService: PostServiceService) {}

  ngOnInit(): void {
    this.getCategories();  // Fetch categories when the component loads
  }

  // Fetch all categories from the backend
  getCategories(): void {
    this.categoryService.getCategories().subscribe(
      (response) => {
        this.categories = response;
      },
      (error) => {
        this.errorMessage = 'Failed to load categories. Please try again.';
      }
    );

  }
  // Delete a category by ID
  onDelete(id: number): void {
    if (confirm('Are you sure you want to delete this category?')) {
      this.categoryService.deleteCategory(id).subscribe(
        (response) => {
          this.successMessage = 'Category deleted successfully!';
          this.getCategories();  // Refresh the list after deletion
        },
        (error) => {
          this.errorMessage = 'Failed to delete category. Please try again.';
        }
      );
    }
  }

  // Update a category (for simplicity, you can open a modal or navigate to an update form)
  onUpdate(category: Category): void {
    // For simplicity, we'll just log the category for now
    console.log('Updating category:', category);
    // You can implement a modal or redirect to an update form with the category data
  }
}
