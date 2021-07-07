import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
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
  constructor(private profileService:ProfileService, private toastr : ToastrService) { }

  ngOnInit( ): void {
    this.profileService.getProfilesForSettings(1).subscribe(data=>{console.log(data); this.listOfDataProfiles=data;});
  }

  mutePost(item : string ){
    this.profileService.mutePost(1, item).subscribe(data=>this.toastr.success("Successfully muted posts from profile "+item));
    this.ngOnInit( );
  }
  muteStory(item : string ){
    this.profileService.muteStory(1, item).subscribe(data=>this.toastr.success("Successfully muted stories from profile "+item));
    this.ngOnInit( );
  }
  muteComment(item : string ){
    this.profileService.muteComment(1, item).subscribe(data=>this.toastr.success("Successfully muted comments from profile "+item));
    this.ngOnInit( );
  }
  muteMessage(item : string ){
    this.profileService.muteMessage(1, item).subscribe(data=>this.toastr.success("Successfully muted messages from profile "+item));
    this.ngOnInit( );
  }
  unmutePost(item : string ){
    this.profileService.unmutePost(1, item).subscribe(data=>this.toastr.success("Successfully unmuted posts from profile "+item));
    this.ngOnInit( );
  }
  unmuteStory(item : string ){
    this.profileService.unmuteStory(1, item).subscribe(data=>this.toastr.success("Successfully unmuted stories from profile "+item));
    this.ngOnInit( );
  }
  unmuteComment(item : string ){
    this.profileService.unmuteComment(1, item).subscribe(data=>this.toastr.success("Successfully unmuted comments from profile "+item));
    this.ngOnInit( );
  }
  unmuteMessage(item : string ){
    this.profileService.unmuteMessage(1, item).subscribe(data=>this.toastr.success("Successfully unmuted messages from profile "+item));
    this.ngOnInit( );
  }

 
}
