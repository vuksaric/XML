import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-close-friends',
  templateUrl: './close-friends.component.html',
  styleUrls: ['./close-friends.component.css']
})
export class CloseFriendsComponent implements OnInit {
  data = [
    'Racing car sprays burning fuel into crowd.',
    'Japanese princess to wed commoner.',
    'Australian walks 100km after outback crash.',
    'Man charged over missing wedding girl.',
    'Los Angeles battles huge wildfires.'
  ];
  closeFriends : string[] = [];
  searchValue = '';
  decoded_token : any;

  constructor(private profileService : ProfileService,  private toastr : ToastrService, private authService : AuthService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.profileService.getCloseFriends(this.decoded_token.id).subscribe(data=>{console.log(data); this.closeFriends= data;});
    this.profileService.getProfilesForCloseFriends(this.decoded_token.id).subscribe(data=>{console.log(data); this.data= data;});
  }
  
  add(item : string){
    this.profileService.addCloseFriend(this.decoded_token.id, item).subscribe(data=>{
      this.toastr.success("Close friend successfully added");
      this.ngOnInit();
    });
    
  }
  remove(item : string){
    this.profileService.removeCloseFriend(this.decoded_token.id, item).subscribe(data=>{
      this.toastr.success("Close friend successfully removed");
      this.ngOnInit();
    });
    
  }

}
