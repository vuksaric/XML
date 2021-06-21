import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { AuthService } from 'src/app/services/auth.service';
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
  listOfData : any;
  profile : any;
  userInfo : any;
  checkFollowing : any;
  listOfColumn = [
    {
      title: '',
    }
  ];
  constructor(private profileService: ProfileService, private postStoryService: PostStoryService, private imageService: ImageService, 
    private sanitizer: DomSanitizer, private authService : AuthService) { }

  getSrc(pictureId: any) : String{
    this.imageService.getImage(pictureId).subscribe(dat => {
      console.log("usao");
      console.log(dat);
      this.pictureLocations = dat;
      console.log(this.pictureLocations);
    });
    return this.pictureLocations;
  }

  sanitizeImageUrl(imageUrl: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
}

  ngOnInit(): void {
    this.profileService.getProfile(1).subscribe(data => {
      //this.listOfData = data;
      this.authService.getById("1").subscribe(data => {
        this.userInfo = data;
        console.log(this.userInfo);
      });
      this.profileService.checkFollowing(1,3).subscribe(data =>{
        this.checkFollowing = data;
      });
      this.profile = data;
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

      console.log(this.profile);
    });


      
  }

  countFollowers() : number
  {
    return this.profile.followers.length;
  }

  countFollowing() : number
  {
    return this.profile.following.length;
  }

  countPosts() : number
  {
    return this.profile.postIds.length;
  }
}