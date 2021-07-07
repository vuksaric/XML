import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
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

  constructor(private profileService : ProfileService,  private toastr : ToastrService) { }

  ngOnInit(): void {
    this.profileService.getCloseFriends(1).subscribe(data=>{console.log(data); this.closeFriends= data;});
    this.profileService.getProfilesForCloseFriends(1).subscribe(data=>{console.log(data); this.data= data;});
  }
  
  add(item : string){
    this.profileService.addCloseFriend(1, item).subscribe(data=>{
      this.toastr.success("Close friend successfully added");
      this.ngOnInit();
    });
    
  }
  remove(item : string){
    this.profileService.removeCloseFriend(1, item).subscribe(data=>{
      this.toastr.success("Close friend successfully removed");
      this.ngOnInit();
    });
    
  }

}
