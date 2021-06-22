import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { differenceInCalendarDays } from 'date-fns';
import { ToastrService } from 'ngx-toastr';

import { ProfileService } from 'src/app/services/profile.service';



@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  validateForm = new FormGroup({
    surname: new FormControl(),
    name: new FormControl(),
    email: new FormControl(),
    username:new FormControl(),
    gender: new FormControl(),
    date: new FormControl(),
    biography: new FormControl(),
    website: new FormControl(),
    privacy: new FormControl(),
    messages: new FormControl(),
    tags: new FormControl(),
    phone : new FormControl(),
    phoneNumberPrefix : new FormControl(),
    activity: new FormControl(),
    story: new FormControl(),
    post: new FormControl(),
    comment : new FormControl(),
    messages_private : new FormControl()
  }); 
  oldUsername!: String;
  usernameChanged = false
  
  
  
  today = new Date();
  constructor(private fb: FormBuilder, private profileService : ProfileService, 
    private toastr : ToastrService) { }

  ngOnInit(): void {
    this.profileService.getProfile2(1).subscribe(data=> {
      console.log(data);this.validateForm = this.fb.group({
        surname: [data.surname,[Validators.required]],
        name: [data.name,[Validators.required]],
        email: [data.email,[Validators.required]],
        username: [data.username,[Validators.required]],
        gender: [data.gender,[Validators.required]],
        date: [new Date(data.dateOfBirth)	,[Validators.required]],
        biography: [data.biography,[Validators.required]],
        website: [data.website,[Validators.required]],
        privacy: [data.isPrivate,[Validators.required]],
        messages: [data.canBeMessaged,[Validators.required]],
        tags: [data.canBeTagged,[Validators.required]],
        phone : [data.phone.substring(4),[Validators.required]],
        phoneNumberPrefix : [data.phone.substring(0,4),[Validators.required]],
        activity: [data.notifyProfileActivity,[Validators.required]],
        story:  [data.notifyStory,[Validators.required]],
        post:  [data.notifyPost,[Validators.required]],
        comment :  [data.notifyComment,[Validators.required]],
        messages_private : [data.canBeMessagedPrivate,[Validators.required]],
      });
      this.oldUsername = data.username;
    });
  }
 
  submitForm() {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    if(this.validateForm.valid){
      if(this.oldUsername==this.validateForm.value.username)
        this.usernameChanged=false
      else
        this.usernameChanged=true

      const body = {
        name: this.validateForm.value.name,
        surname: this.validateForm.value.surname,
        email: this.validateForm.value.email,
        phone: this.validateForm.value.phoneNumberPrefix + this.validateForm.value.phone,
        username: this.validateForm.value.username,
        gender: this.validateForm.value.gender,
        dateOfBirth: this.validateForm.value.date,
        biography: this.validateForm.value.biography,
        website: this.validateForm.value.website,
        isPrivate: this.validateForm.value.privacy,
        canBeMessaged: this.validateForm.value.messages,
        canBeTagged: this.validateForm.value.tags,
        canBeMessagedPrivate: this.validateForm.value.messages_private,
        notifyComment: this.validateForm.value.comment,
        notifyPost: this.validateForm.value.post,
        notifyStory: this.validateForm.value.story,
        notifyProfileActivity: this.validateForm.value.activity,
        id : 1, 
        usernameChanged: this.usernameChanged
      }
      
      this.profileService.editProfile(body).subscribe(data => {
        console.log(data);
        if(data)
          this.toastr.success("Profile successfully edited!");
        else
          this.toastr.error("Username already!");
      });
    }
    
    

  }

  disabledDate = (current: Date): boolean => {
    return differenceInCalendarDays(current, this.today) > 0;
  };

}
