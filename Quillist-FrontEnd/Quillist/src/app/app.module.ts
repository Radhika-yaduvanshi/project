import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminComponent } from './AdminPannel/admin/admin.component';
import { HeaderComponent } from './header/header.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material/slider';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { ManageUsersComponent } from './AdminPannel/ManageUsers/manage-users/manage-users.component';
import { ManagePostsComponent } from './AdminPannel/ManagePosts/manage-posts/manage-posts.component';
import { ModerationComponent } from './AdminPannel/moderation/moderation.component';
import { ManageCategoriesComponent } from './AdminPannel/ManageCategories/manage-categories/manage-categories.component';
import { ManageThemesComponent } from './AdminPannel/manage-themes/manage-themes.component';
import { ManageCommentsComponent } from './AdminPannel/manage-comments/manage-comments.component';
// import { AddUserComponent } from './AdminPannel/add-user/add-user.component';
import { ViewUserComponent } from './AdminPannel/ManageUsers/view-user/view-user.component';
import { DeleteUserComponent } from './AdminPannel/ManageUsers/delete-user/delete-user.component';
import { CreatePostComponent } from './AdminPannel/ManagePosts/create-post/create-post.component';
import { ViewPostComponent } from './AdminPannel/ManagePosts/view-post/view-post.component';
import { UpdatePostComponent } from './AdminPannel/ManagePosts/update-post/update-post.component';
import { DeletePostComponent } from './AdminPannel/ManagePosts/delete-post/delete-post.component';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { EditorModule } from '@tinymce/tinymce-angular'; // TinyMCE Module
import { AddCategoriesComponent } from './AdminPannel/ManageCategories/add-categories/add-categories.component';
import { ViewCategoriesComponent } from './AdminPannel/ManageCategories/view-categories/view-categories.component';
import { UpdateCategoriesComponent } from './AdminPannel/ManageCategories/update-categories/update-categories.component';
import { DeleteCategoriesComponent } from './AdminPannel/ManageCategories/delete-categories/delete-categories.component';
import { AddUserComponent } from './AdminPannel/ManageUsers/add-user/add-user.component';
import { HTTP_INTERCEPTORS, HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';
import { BlogDetailsComponent } from './AdminPannel/ManagePosts/blog-details/blog-details.component';
import { ReactiveFormsModule } from '@angular/forms';  // Import ReactiveFormsModule
import { AdminloginComponent } from './AdminPannel/Authentication/adminlogin/adminlogin.component';
import { ForgotPasswordComponent } from './AdminPannel/Authentication/forgot-password/forgot-password.component';
import { WildCardComponent } from './wild-card/wild-card.component';
import { customInterceptor } from './services/custom.interceptor';


// import { ReactiveFormsModule } from '@angular/forms';
@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    HeaderComponent,
    ManageUsersComponent,
    ManagePostsComponent,
    ModerationComponent,
    ManageCategoriesComponent,
    ManageThemesComponent,
    ManageCommentsComponent,
    ViewUserComponent,
    DeleteUserComponent,
    CreatePostComponent,
    ViewPostComponent,
    UpdatePostComponent,
    DeletePostComponent,
    AddCategoriesComponent,
    ViewCategoriesComponent,
    UpdateCategoriesComponent,
    DeleteCategoriesComponent,
    AddUserComponent,
    ViewUserComponent,
    BlogDetailsComponent,
    AdminloginComponent,
    ForgotPasswordComponent,
    WildCardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    MatSlideToggleModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatInputModule,
    MatButtonModule,
    RouterOutlet,
    RouterLink,
    RouterModule,
    CKEditorModule,
    EditorModule,
    HttpClientModule,
    NgxPaginationModule,
    ReactiveFormsModule

    // ReactiveFormsModule,
  ],
  // providers: [provideHttpClient(withInterceptors([customInterceptor]))],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: customInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
