import { Component, OnInit } from '@angular/core';
import { ImageService } from 'src/app/services/image.service';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';

export interface Profile{
  username: string;
  biography: string;
  website: string;
}

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent implements OnInit {
  pictureId: any;
  pictureLocations!: any;
  postIds!: any;
  listOfData! : any;
  listOfColumn = [
    {
      title: '',
    }
  ];
  constructor(private profileService: ProfileService, private postStoryService: PostStoryService, private imageService: ImageService) { }

  getSrc(pictureId: any){
    console.log("getSrc");
    this.imageService.getImage(pictureId).subscribe(dat => {
      console.log("usao");
      console.log(dat);
      this.pictureLocations = dat;
      console.log(this.pictureLocations);
    });
    return this.pictureLocations;
  }

  ngOnInit(): void {
    this.profileService.getProfile(11).subscribe(data => {
      //this.listOfData = data;
      this.postIds = data.postIds;
      console.log(data.postIds);
      
      const body = {
        postIds: this.postIds
      }
      console.log(body);
      this.postStoryService.getPosts(body).subscribe(data => {
          console.log(data);
          this.listOfData = data;
      });
    });


      
  }
}