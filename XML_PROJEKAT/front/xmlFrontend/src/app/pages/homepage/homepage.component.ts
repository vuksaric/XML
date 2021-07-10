import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

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
    if(this.decoded_token.agent == "true")
    {
        this.toastrService.error("Restricted access");
        this.router.navigate(['login']);
    }

  }

  Feed(){
    this.router.navigate(['homepage/feed']);
  }

  Profile() {
    this.router.navigate(['homepage/profile']);
  }
  
  Search(){
    this.router.navigate(['homepage/search']);
  }

  logout(){
    //ovde obrisati sve sto se tice tokena
    localStorage.clear();
    this.router.navigate(['login']);
  }

  NewPost(){
    this.router.navigate(['homepage/new-post']);
  }

  NewStory(){
    this.router.navigate(['homepage/new-story']);
  }
  NewAlbum(){
    this.router.navigate(['homepage/new-album']);
  }
  NewVerificationRequest(){
    this.router.navigate(['homepage/new-verification-request']);
  }
  viewLiked(){
    this.router.navigate(['homepage/view-liked']);
  }
  viewFavourites(){
    this.router.navigate(['homepage/view-favourites']);
  }
  closeFriends(){
    this.router.navigate(['homepage/close-friends']);
  }

  notificationSetings(){
    this.router.navigate(['homepage/notification-settings']);
  }

  myProfile()
  {
    this.router.navigate(['my-profile']);
  }

}
