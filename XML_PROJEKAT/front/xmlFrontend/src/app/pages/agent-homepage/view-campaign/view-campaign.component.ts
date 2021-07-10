import { Component, OnInit } from '@angular/core';
import { CampaignService } from 'src/app/services/campaign.service';
import { differenceInCalendarDays } from 'date-fns';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-view-campaign',
  templateUrl: './view-campaign.component.html',
  styleUrls: ['./view-campaign.component.css']
})
export class ViewCampaignComponent implements OnInit {

  listOfData : any;
  typeCampaign = false;
  startDate : any;
  endDate : any;
  repeatCount  = 1;
  typeCommercial : any;
  gender : any;
  minAge : any;
  maxAge : any;
  typeUser : any;
  tags : string[] = [];
  isVisible = false;
  today = new Date();
  currentId : any;
  decoded_token : any;

  constructor(private toastr : ToastrService,private router: Router, private campaignService : CampaignService, private authService : AuthService) { }

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

    this.campaignService.getAll().subscribe(data=>{
      this.listOfData = data;
    });

  }

    

  disabledDateStart = (current: Date): boolean => {
    return differenceInCalendarDays(current, this.today) < 0;
  };

  disabledDateEnd = (current: Date): boolean => {
    return differenceInCalendarDays(current, this.startDate) < 0;
  };

  edit(data : any)
  {
    console.log(data);
    this.isVisible = true;
    if(data.endDate == null)
      this.typeCampaign = true;
    else
      this.typeCampaign = false;
    this.startDate = new Date(data.startDate);
    this.endDate = new Date(data.endDate);
    this.repeatCount = data.repeatCount;
    this.typeCommercial = !data.isItPost;
    this.gender = data.targetGroup.gender;
    this.minAge = data.targetGroup.startAge;
    this.maxAge = data.targetGroup.endAge;
    this.typeUser = data.targetGroup.profileCategory;
    this.currentId = data.id;

  }

  delete(data : any)
  {
    this.campaignService.delete(data).subscribe(data =>{
      this.ngOnInit();
    })
  }

  submit()
  {
    const targetGroup = {
      gender : this.gender,
      startAge : this.minAge,
      endAge : this.maxAge,
      profileCategory : this.typeUser
    }

    const body = {
      id : this.currentId,
      startDate : this.startDate,
      endDate : this.endDate,
      countAllowed : this.repeatCount,
      isItPost : !this.typeCommercial,
      targetGroup : targetGroup
    }

    this.campaignService.edit(body).subscribe(data =>{
      this.isVisible = false;
      this.ngOnInit();
    })
  }

  handleCancel()
  {
    this.isVisible = false;
  }

  canBeEdited(data : any) : boolean
  {
    var date = new Date(data.changedDate);
    date.setDate(date.getDate()+2);
    if(this.today < date)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

}
