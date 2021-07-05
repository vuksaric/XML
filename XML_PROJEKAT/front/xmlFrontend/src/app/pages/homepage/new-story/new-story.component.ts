import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-new-story',
  templateUrl: './new-story.component.html',
  styleUrls: ['./new-story.component.css']
})
export class NewStoryComponent implements OnInit {
  tags : string[] = [];
  isVisible = false;
  usernames: string[] = [];
  searchValue = '';
  validateForm!: FormGroup; 
  file :  File[] = [];
  listOfControl: Array<{ id: number; controlInstance: string }> = [];
  
  constructor(private fb: FormBuilder, private postStory : PostStoryService,  private toastr : ToastrService,
    private profileService: ProfileService) {}
  
  ngOnInit() {
       /* Initiate the form structure */
    this.validateForm = this.fb.group({
      location: [null,[Validators.required]],
      caption: [null,[Validators.required]],
      files: this.fb.array([this.fb.group({point:''})]),
      close_friends: [null,[Validators.required]],
      highlight: [null,[Validators.required]]
    })
    this.addField();
    this.profileService.getProfilesForTagging(1).subscribe(data=>{console.log(data);
      this.usernames=data;
    });
  }
  addField(e?: MouseEvent): void {
    if (e) {
      e.preventDefault();
    }

    if(this.listOfControl.length<10){
      const id = this.listOfControl.length > 0 ? this.listOfControl[this.listOfControl.length - 1].id + 1 : 0;

      const control = {
        id,
        controlInstance: `passenger${id}`
      };
      const index = this.listOfControl.push(control);
      console.log(this.listOfControl[this.listOfControl.length - 1]);
      this.validateForm.addControl(this.listOfControl[index - 1].controlInstance, new FormControl(null, Validators.required));
    }

   
  }

  removeField(i: { id: number; controlInstance: string }, e: MouseEvent): void {
    e.preventDefault();
    if (this.listOfControl.length > 1) {
      const index = this.listOfControl.indexOf(i);
      this.listOfControl.splice(index, 1);
      console.log(this.listOfControl);
      this.validateForm.removeControl(i.controlInstance);
    }
  }
  
  fileChange(event: any) {
    // Instantiate an object to read the file content
    let reader = new FileReader();
    // when the load event is fired and the file not empty
    if(event.target.files && event.target.files.length > 0) {
      // Fill file variable with the file content
      this.file.push(event.target.files[0]);
    }
  }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }


     // Instantiate a FormData to store form fields and encode the file
     let body = new FormData();
     // Add file content to prepare the request
  

    this.file.forEach(file => body.append("file", file));  
     body.append("location", this.validateForm.value.location);
     body.append("caption",  this.validateForm.value.caption);
     body.append("close_friends", this.validateForm.value.close_friends);
     body.append("highlight",  this.validateForm.value.highlight);
     body.append("userInfoId", "1");
     if(this.tags.length==0)
     body.append("tags", "");
    else
     this.tags.forEach(tag => body.append("tags", tag));

     console.log(body.getAll('file'));
     console.log(body.getAll('close_friends'));
     console.log(body.getAll('highlight'));
     // Launch post request
     if(this.validateForm.valid){
       this.postStory.creatStory(body)
       .subscribe(
         // Admire results
         (data) => {console.log(data)},
         // Or errors :-(
         error => {console.log(error);  this.toastr.error("Error while publishing story!");},
         // tell us if it's finished
         () => { console.log("completed"); this.toastr.success("Story successfully published!"); }
       );


     }
   
  }
  handleClose(removedTag: any): void {
    this.tags = this.tags.filter(tag => tag !== removedTag);
    this.usernames.push(removedTag);
  }

  sliceTagName(tag: string): string {
    const isLongTag = tag.length > 20;
    return isLongTag ? `${tag.slice(0, 20)}...` : tag;
  }

  tag(item : any){
    this.tags.push(item);
    this.usernames = this.usernames.filter(username => username!=item);
    console.log(this.tags);
    console.log(this.usernames);

  }

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    console.log('Button ok clicked!');
    this.isVisible = false;
    this.searchValue = '';
  }

  handleCancel(): void {
    console.log('Button cancel clicked!');
    this.isVisible = false;
  }
  
  }