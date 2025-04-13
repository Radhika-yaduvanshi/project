import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './AdminPannel/admin/admin.component';
import { AdminloginComponent } from './AdminPannel/Authentication/adminlogin/adminlogin.component';
import { ManageUsersComponent } from './AdminPannel/ManageUsers/manage-users/manage-users.component';
import { ManageCategoriesComponent } from './AdminPannel/ManageCategories/manage-categories/manage-categories.component';
import { ManagePostsComponent } from './AdminPannel/ManagePosts/manage-posts/manage-posts.component';
import { ManageThemesComponent } from './AdminPannel/manage-themes/manage-themes.component';
import { ManageCommentsComponent } from './AdminPannel/manage-comments/manage-comments.component';
import { ModerationComponent } from './AdminPannel/moderation/moderation.component';
// import { AddUserComponent } from './AdminPannel/add-user/add-user.component';
import { ViewUserComponent } from './AdminPannel/ManageUsers/view-user/view-user.component';
import { DeleteUserComponent } from './AdminPannel/ManageUsers/delete-user/delete-user.component';
import { CreatePostComponent } from './AdminPannel/ManagePosts/create-post/create-post.component';
import { ViewPostComponent } from './AdminPannel/ManagePosts/view-post/view-post.component';
import { DeletePostComponent } from './AdminPannel/ManagePosts/delete-post/delete-post.component';
import { AddUserComponent } from './AdminPannel/ManageUsers/add-user/add-user.component';
import { UpdateCategoriesComponent } from './AdminPannel/ManageCategories/update-categories/update-categories.component';
import { AddCategoriesComponent } from './AdminPannel/ManageCategories/add-categories/add-categories.component';
import { ViewCategoriesComponent } from './AdminPannel/ManageCategories/view-categories/view-categories.component';
import { DeleteCategoriesComponent } from './AdminPannel/ManageCategories/delete-categories/delete-categories.component';
import { BlogDetailsComponent } from './AdminPannel/ManagePosts/blog-details/blog-details.component';
import { ForgotPasswordComponent } from './AdminPannel/Authentication/forgot-password/forgot-password.component';
import { WildCardComponent } from './wild-card/wild-card.component';
import { authGuard } from './services/auth.guard';
import { AdminDashboardComponent } from './AdminPannel/admin-dashboard/admin-dashboard.component';
import { AdminProfileComponent } from './AdminPannel/admin-profile/admin-profile.component';
const routes: Routes = [
  {
    path: '',
    // component: AdminloginComponent,
    component: AdminComponent,
    // component:AdminDashboardComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '', // 👈 default child
        component: AdminDashboardComponent,
      },
      {
        path: 'manage-users',
        component: ManageUsersComponent,
        children: [
          // { path: '', component: AdminDashboardComponent },
          { path: '', redirectTo: 'view-users', pathMatch: 'full' },
          { path: 'add-user', component: AddUserComponent },
          { path: 'view-user', component: ViewUserComponent },
          { path: 'delete-user', component: DeleteUserComponent },
        ],
      },
      {
        path: 'manage-posts',
        component: ManagePostsComponent,
        children: [
          { path: '', redirectTo: 'view-post', pathMatch: 'full' },
          { path: 'create-post', component: CreatePostComponent },
          { path: 'view-post', component: ViewPostComponent },
          { path: 'view-post/:id', component: BlogDetailsComponent },
          { path: 'delete-post', component: DeletePostComponent },
        ],
      },
      {
        path: 'manage-categories',
        component: ManageCategoriesComponent,
        children: [
          { path: '', redirectTo: 'view-post', pathMatch: 'full' },
          { path: 'add-categories', component: AddCategoriesComponent },
          { path: 'view-categories', component: ViewCategoriesComponent },
          { path: 'delete-categories', component: DeleteCategoriesComponent },
          { path: 'update-categories', component: UpdateCategoriesComponent },
        ],
      },
      {
        // from here modificatin is pending
        path: 'manage-themes',
        component: ManageCategoriesComponent,
        children: [
          { path: '', redirectTo: 'view-post', pathMatch: 'full' },
          { path: 'add-themes', component: AddCategoriesComponent },
          { path: 'view-themes', component: ViewCategoriesComponent },
          { path: 'delete-themes', component: DeleteCategoriesComponent },
          { path: 'update-themes', component: UpdateCategoriesComponent },
        ],
      },
      {
        path: 'manage-comments',
        component: ManageCategoriesComponent,
        children: [
          { path: '', redirectTo: 'view-post', pathMatch: 'full' },
          { path: 'admin-profile', component: AdminProfileComponent },
          { path: 'add-comments', component: AddCategoriesComponent },
          { path: 'view-comments', component: ViewCategoriesComponent },
          { path: 'delete-comments', component: DeleteCategoriesComponent },
          { path: 'update-comments', component: UpdateCategoriesComponent },
        ],
      },
    ],
  },
  // { path: 'admin', component: AdminComponent },
  { path: 'adminlogin', component: AdminloginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  // { path: 'manage-categories', component: ManageCategoriesComponent },
  // { path: 'manage-posts', component: ManagePostsComponent },
  { path: 'manage-themes', component: ManageThemesComponent },
  {
    path: 'admin-profile',
    canActivate: [authGuard],
    component: AdminProfileComponent,
  },
  { path: 'manage-comments', component: ManageCommentsComponent },
  { path: 'moderation', component: ModerationComponent },
  { path: 'dashboard', component: AdminDashboardComponent },
  { path: '**', component: WildCardComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
