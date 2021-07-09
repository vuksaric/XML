import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-notification-settings',
  templateUrl: './notification-settings.component.html',
  styleUrls: ['./notification-settings.component.css']
})
export class NotificationSettingsComponent implements OnInit {
  listOfDataProfiles : any[]=[];
  searchValue='';
  checkPost =false;
  isVisible = false;
  decoded_token : any;
  constructor(private profileService:ProfileService, private toastr : ToastrService, private authService : AuthService) { }

  ngOnInit( ): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.profileService.getProfilesForSettings(this.decoded_token.id).subscribe(data=>{console.log(data); this.listOfDataProfiles=data;});
  }

  mutePost(item : string ){
    this.profileService.mutePost(this.decoded_token.id, item).subscribe(data=>this.toastr.success("Successfully muted posts from profile "+item));
    this.ngOnInit( );
  }
  muteStory(item : string ){
    this.profileService.muteStory(this.decoded_token.id, item).subscribe(data=>this.toastr.success("Successfully muted stories from profile "+item));
    this.ngOnInit( );
  }
  muteComment(item : string ){
    this.profileService.muteComment(this.decoded_token.id, item).subscribe(data=>this.toastr.success("Successfully muted comments from profile "+item));
    this.ngOnInit( );
  }
  muteMessage(item : string ){
    this.profileService.muteMessage(this.decoded_token.id, item).subscribe(data=>this.toastr.success("Successfully muted messages from profile "+item));
    this.ngOnInit( );
  }
  unmutePost(item : string ){
    this.profileService.unmutePost(this.decoded_token.id, item).subscribe(data=>this.toastr.success("Successfully unmuted posts from profile "+item));
    this.ngOnInit( );
  }
  unmuteStory(item : string ){
    this.profileService.unmuteStory(this.decoded_token.id, item).subscribe(data=>this.toastr.success("Successfully unmuted stories from profile "+item));
    this.ngOnInit( );
  }
  unmuteComment(item : string ){
    this.profileService.unmuteComment(this.decoded_token.id, item).subscribe(data=>this.toastr.success("Successfully unmuted comments from profile "+item));
    this.ngOnInit( );
  }
  unmuteMessage(item : string ){
    this.profileService.unmuteMessage(this.decoded_token.id, item).subscribe(data=>this.toastr.success("Successfully unmuted messages from profile "+item));
    this.ngOnInit( );
  }

 
}
