import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-agent-homepage',
  templateUrl: './agent-homepage.component.html',
  styleUrls: ['./agent-homepage.component.css']
})
export class AgentHomepageComponent implements OnInit {

  isCollapsed = false;
  userID: number = 0;
  decoded_token : any;

  constructor(private router: Router, private authService : AuthService, private toastrService : ToastrService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    if(this.decoded_token== null)
    {
        this.toastrService.error("Restricted access");
        this.router.navigate(['login']);
    }
    if(this.decoded_token.username == "admin")
    {
        this.toastrService.error("Restricted access");
        this.router.navigate(['login']);
    }
    if(this.decoded_token.agent != true)
    {
        this.toastrService.error("Restricted access");
        this.router.navigate(['login']);
    }

  }

  Feed(){
    this.router.navigate(['agent/feed']);
  }

  Profile() {
    this.router.navigate(['agent/profile']);
  }
  
  Search(){
    this.router.navigate(['agent/search']);
  }

  logout(){
    //ovde obrisati sve sto se tice tokena
    //localStorage.clear();
    this.router.navigate(['login']);
  }

  NewPost(){
    this.router.navigate(['agent/new-post']);
  }

  NewStory(){
    this.router.navigate(['agent/new-story']);
  }
  NewAlbum(){
    this.router.navigate(['agent/new-album']);
  }
  NewVerificationRequest(){
    this.router.navigate(['agent/new-verification-request']);
  }
  viewLiked(){
    this.router.navigate(['agent/view-liked']);
  }
  viewFavourites(){
    this.router.navigate(['agent/view-favourites']);
  }
  closeFriends(){
    this.router.navigate(['agent/close-friends']);
  }

  notificationSetings(){
    this.router.navigate(['agent/notification-settings']);
  }

  myProfile()
  {
    this.router.navigate(['my-profile']);
  }

  newCampaign()
  {
    this.router.navigate(['agent/new-campaign']);
  }

  viewCampaign()
  {
    this.router.navigate(['agent/view-campaign']);
  }

}
