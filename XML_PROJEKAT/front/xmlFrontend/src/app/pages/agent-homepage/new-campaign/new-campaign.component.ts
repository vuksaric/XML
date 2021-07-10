import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { CampaignService } from 'src/app/services/campaign.service';
import { CommercialService } from 'src/app/services/commercial.service';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';
import { differenceInCalendarDays } from 'date-fns';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-campaign',
  templateUrl: './new-campaign.component.html',
  styleUrls: ['./new-campaign.component.css']
})
export class NewCampaignComponent implements OnInit {

  typeCampaign = false;
  startDate : any;
  endDate : any;
  repeatCount  = 1;
  typeCommercial = false;
  gender : any;
  minAge : any;
  maxAge : any;
  typeUser : any;
  tags : string[] = [];
  isVisible = false;
  usernames: string[] = [];
  searchValue = '';
  validateForm!: FormGroup; 
  file :  File[] = [];
  listOfControl: Array<{ id: number; controlInstance: string }> = [];
  decoded_token : any;
  isVisiblePost = false;
  commercials : any[] = [];
  today = new Date();

  constructor(private router: Router, private campaignService : CampaignService,private fb: FormBuilder, private postStory : PostStoryService,  
    private toastr : ToastrService, private profileService: ProfileService, private authService : AuthService, private commercialService : CommercialService) { }

  ngOnInit(): void {

    this.decoded_token = this.authService.getDataFromToken();
    if(this.decoded_token== null)
    {
        this.toastr.error("Restricted access");
        this.router.navigate(['login']);
    }
    if(this.decoded_token.username == "admin")
    {
        this.toastr.error("Restricted access");
        this.router.navigate(['login']);
    }
    if(this.decoded_token.agent != true)
    {
        this.toastr.error("Restricted access");
        this.router.navigate(['login']);
    }
    /* Initiate the form structure */
    this.validateForm = this.fb.group({
      website: [null],
      caption: [null,[Validators.required]],
      files: this.fb.array([this.fb.group({point:''})]),
   
    })
    this.profileService.getProfilesForTagging(this.decoded_token.id).subscribe(data=>{console.log(data);
      this.usernames=data;

    });

  }

  
  disabledDateStart = (current: Date): boolean => {
    return differenceInCalendarDays(current, this.today) < 0;
  };

  disabledDateEnd = (current: Date): boolean => {
    return differenceInCalendarDays(current, this.startDate) < 0;
  };

  newCampaign()
  {
    var today = new Date();
    const targetGroup = {
      gender : this.gender,
      startAge : this.minAge,
      endAge : this.maxAge,
      profileCategory : this.typeUser
    }

    const body = {
      startDate : this.startDate,
      changedDate : today,
      endDate : this.endDate,
      countAllowed : this.repeatCount,
      countDone : 0,
      isItPost : !this.typeCommercial,
      commercials : this.commercials,
      targetGroup : targetGroup,
      username : this.decoded_token.username
    }
    this.campaignService.new(body).subscribe(data=>{
      this.toastr.success("Campaign made")
    })
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
     body.append("userInfoId",this.decoded_token.id)
     body.append("caption",  this.validateForm.value.caption);
     if(this.tags.length==0)
      body.append("tags", "");
     else
      this.tags.forEach(tag => body.append("tags", tag));

     console.log(body.getAll('tags'));
     // Launch post request
      if(this.typeCommercial)
      {
        this.postStory.createStoryCommercial(body).subscribe(data=>{
          const body = {
            postId : data,
            website : this.validateForm.value.website
          }
          this.commercialService.new(body).subscribe(data=>{
              this.commercials.push(data);
              this.toastr.success("Commercial made")
          })
        })
      }
      else
      {
        this.postStory.createPostCommercial(body).subscribe(data=>{
          const body = {
            postId : data,
            website : this.validateForm.value.website
          }
          this.commercialService.new(body).subscribe(data=>{
              this.commercials.push(data);
              this.toastr.success("Commercial made")
          })
        })
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

  handleCancelPost()
  {
    this.isVisiblePost = false;
  }

  addContent()
  {
    this.isVisiblePost = true;
  }



}
