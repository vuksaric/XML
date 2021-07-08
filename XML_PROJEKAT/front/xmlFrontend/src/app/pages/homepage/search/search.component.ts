import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
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
  decoded_token : any;
 

  constructor(private profileService: ProfileService, private router: Router, private postStory: PostStoryService, private authService : AuthService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.profileService.getPublicProfiles().subscribe(data=>{console.log(data); this.listOfDataProfiles=data;})
    this.profileService.getProfilesForTagging(this.decoded_token.id).subscribe(data=>{console.log(data); this.listOfDataTags=data;})
    this.postStory.getLocations(this.decoded_token.id).subscribe(data=>{console.log(data); this.listOfDataLocations=data;})

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
    this.postStory.getPostsByLocation(this.decoded_token.id,item).subscribe(data=>{console.log(data); this.listOfDataLocation=data});
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

  @ViewChild('videoPlayer') 
  videoplayer!: ElementRef;
  toggleVideo() {
      this.videoplayer.nativeElement.play();
  }

}
