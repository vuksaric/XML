import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { FollowRequestService } from 'src/app/services/follow-request.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-follow-requests',
  templateUrl: './follow-requests.component.html',
  styleUrls: ['./follow-requests.component.css']
})
export class FollowRequestsComponent implements OnInit {

  listOfData : any;
  decoded_token : any;

  constructor(private followRequestService : FollowRequestService,private profileService: ProfileService, private authService : AuthService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.followRequestService.getAllForProfile(this.decoded_token.id).subscribe(data =>{
      this.listOfData = data
    })
  }

  accept(id : number) : void
  {
    this.profileService.acceptFollowRequest(this.decoded_token.id,id).subscribe(data =>{
      this.listOfData = data
    })
  }

  deny(id : number) : void
  {
    this.profileService.denyFollowRequest(this.decoded_token.id,id).subscribe(data =>{
      this.listOfData = data
    })
  }

}
