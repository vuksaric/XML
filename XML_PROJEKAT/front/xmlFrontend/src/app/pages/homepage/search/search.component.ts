import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchValue: string = "";
  listOfData: any[]=[];

  constructor(private profileService: ProfileService, private router: Router) { }

  ngOnInit(): void {
    this.profileService.getPublicProfiles().subscribe(data=>{console.log(data); this.listOfData=data;})
  }

  view(item : string){
    this.router.navigate(['view-profile/'+item]);
  }

}
