import { Component, OnInit } from '@angular/core';
import { FollowRequestService } from 'src/app/services/follow-request.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-follow-requests',
  templateUrl: './follow-requests.component.html',
  styleUrls: ['./follow-requests.component.css']
})
export class FollowRequestsComponent implements OnInit {

  listOfData : any;

  constructor(private followRequestService : FollowRequestService,private profileService: ProfileService) { }

  ngOnInit(): void {
    this.followRequestService.getAllForProfile(3).subscribe(data =>{
      this.listOfData = data
    })
  }

  accept(id : number) : void
  {
    this.profileService.acceptFollowRequest(3,id).subscribe(data =>{
      this.listOfData = data
    })
  }

  deny(id : number) : void
  {
    this.profileService.denyFollowRequest(3,id).subscribe(data =>{
      this.listOfData = data
    })
  }

}
