import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UserServicesService } from '../../services/user-services.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-profile',
  standalone: false,
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.css'
})
export class EditProfileComponent implements OnInit{


profileForm!: FormGroup<any>;

userService=inject(UserServicesService)
fb=inject(FormBuilder)
router=inject(Router)
route=inject(ActivatedRoute)

userId!:number;
selectedFile:File|null=null;


// this.userId = +this.route.snapshot.paramMap.get('id')!;

ngOnInit(): void {
  // this.userId = +this.route.snapshot.paramMap.get('id')!;
  const idParam =+this.route.snapshot.paramMap.get('id')!;
  if(idParam){
    this.userId=+idParam;

  }else{
    console.error("user id not found")
  }
  console.log("user id in nginit: "+this.userId);
  
  console.log("ngoninit");
  

  this.profileForm=this.fb.group({
    id:[this.userId],
    userName:['',],
    email:[''],
    password:[''],
    profilePhoto:[null]
  });
  this.getUserData();
}
  getUserData() {
    this.userService.getUserById(this.userId).subscribe({
      next:(user)=>{
        console.log("user name : "+user.userName);
        console.log("user id : "+user.id);
        this.profileForm.patchValue({
          userName:user.userName,
          email:user.email,
          password:user.password,
          profilePhoto:user.profilePhoto
        })
      }
    })
  }

onFileSelected(event: any) {
  this.selectedFile=event.target.files[0];
  }
  onSubmit() {
  const formdata= new FormData();
  // console.log("here");
  
  // console.log(this.userId);
  // console.log(this.profileForm.get('userName')?.value);
  // console.log(this.profileForm.get('email')?.value);
  // console.log(this.profileForm.get('password')?.value);
  
  // formdata.append('userName',this.profileForm.get('userName')?.value);
  // formdata.append('email',this.profileForm.get('email')?.value);
  // formdata.append('password',this.profileForm.get('password')?.value);

  const payload={
    userName: this.profileForm.get('userName')?.value,
    email: this.profileForm.get('email')?.value,
    password: this.profileForm.get('password')?.value,
    // profilePhoto:this.profileForm.get('profilePhoto')?.value,/

    
  }
  formdata.append('user', new Blob([JSON.stringify(payload)], { type: 'application/json' }));
  if (this.selectedFile) {
    formdata.append('profilePhoto', this.selectedFile, this.selectedFile.name);
    // profilePhoto:this.profileForm.get('profilePhoto')?.value
}


  // this.userService.updateUser(formdata,this.userId).subscribe({
  //   next:(user)=>{
  //     console.log("user ID in onsubmit : "+this.userId);
      
  //     // console.log("user id : "+user.id);
      
  //     alert("Profile updated successfully .....");
  //     this.router.navigate(['/admin-profile'])
  //   },
  //   error:(error)=>{
  //     console.log(error);
  //     alert("Faliled to update profile ......");
      
  //   }
  // })
  }


}
