import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { FollowRequestService } from 'src/app/services/follow-request.service';
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
  checkMuted : any;
  checkBlocked : any;
  checkRequest : any;
  listOfColumn = [
    {
      title: '',
    }
  ];
  constructor(private profileService: ProfileService, private postStoryService: PostStoryService, private imageService: ImageService, 
    private sanitizer: DomSanitizer, private authService : AuthService,private activatedRoute: ActivatedRoute,
     private followRequestService : FollowRequestService) { }

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
    this.profileService.getProfile(this.activatedRoute.snapshot.paramMap.get('username')).subscribe(data => {
      this.profileService.checkFollowing(1,3).subscribe(data =>{
        this.checkFollowing = data;
      });

      this.profileService.checkMuted(1,3).subscribe(data =>{
        this.checkMuted = data;
      });

      this.profileService.checkBlocked(1,3).subscribe(data =>{
        this.checkBlocked = data;
      });


      this.followRequestService.checkRequest(1,3).subscribe(data=>{
        this.checkRequest = data;
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

  follow() : void
  {
    this.profileService.followProfile(1,3).subscribe(data =>{
        location.reload();
    });
  }

  unfollow() : void
  {
    this.profileService.unfollowProfile(1,3).subscribe(data =>{
      location.reload();
    });
  }

  mute() : void
  {
    this.profileService.muteProfile(1,3).subscribe(data =>{
      location.reload();
    });
  }

  unmute() : void
  {
    this.profileService.unmuteProfile(1,3).subscribe(data =>{
      location.reload();
    });
  }

  block() : void
  {
    this.profileService.blockProfile(1,3).subscribe(data =>{
      this.profileService.checkFollowing(1,3).subscribe(data =>{
        this.checkFollowing = data;
      });
      location.reload();
      console.log(this.checkFollowing);
    });
  }

  sendRequest() : void
  {
    const body = {
      accepted : false,
      fromProfileId : 1,
      toProfileId : 3
    }
    this.followRequestService.newRequest(body).subscribe(data =>{
      location.reload();
    })
  }


}