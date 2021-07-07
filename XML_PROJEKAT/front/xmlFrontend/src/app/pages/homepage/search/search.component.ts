import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})

export class SearchComponent implements OnInit {
  searchValue: string = "";
  listOfDataProfiles: any[]=[];
  searchValueTags: string = "";
  listOfDataTags: any[]=[];
  searchValueLocations : string ='';
  listOfDataLocations : any[]=[];

  listOfData : any;
  listOfDataLocation : any;
 

  constructor(private profileService: ProfileService, private router: Router, private postStory: PostStoryService) { }

  ngOnInit(): void {
    this.profileService.getPublicProfiles().subscribe(data=>{console.log(data); this.listOfDataProfiles=data;})
    this.profileService.getProfilesForTagging(1).subscribe(data=>{console.log(data); this.listOfDataTags=data;})
    this.postStory.getLocations(1).subscribe(data=>{console.log(data); this.listOfDataLocations=data;})

  }

  view(item : string){
    this.router.navigate(['view-profile/'+item]);
  }

  viewTags(item : string){
    this.listOfData=[];
    this.postStory.getTagsPost(item).subscribe(data=>{console.log(data); this.listOfData=data});
  }
  viewLocations(item : string){
    this.listOfDataLocation=[];
    this.postStory.getPostsByLocation(1,item).subscribe(data=>{console.log(data); this.listOfDataLocation=data});
  }

  countLikes(data : any) : number
  {
    return data.likeIds.length;
  }

  countDislikes(data : any) : number
  {
    return data.dislikeIds.length;
  }

  countComments(data : any) : number
  {
    return data.comments.length;
  }

}
