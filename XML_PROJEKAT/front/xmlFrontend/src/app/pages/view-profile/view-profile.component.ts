import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { AuthService } from 'src/app/services/auth.service';
import { FollowRequestService } from 'src/app/services/follow-request.service';
import { ImageService } from 'src/app/services/image.service';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';
import { formatDistance } from 'date-fns';


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
  checkRequest : any;
  likesCount : any;
  dislikesCount : any;
  commentsCount : any;
  comments : any;
  likes = 0;
  currentPostId : any;
  liked = false;
  disliked = false;
  submitting = false;
  inputValue = '';
  data: any[] = [];
  reported = false;
  user = {
    author: 'Han Solo',
    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png'
  };
  listOfColumn = [
    {
      title: '',
    }
  ];
  isVisible = false;
  location : any;
  constructor(private profileService: ProfileService, private postStoryService: PostStoryService, private imageService: ImageService, 
    private sanitizer: DomSanitizer, private authService : AuthService, private followRequestService : FollowRequestService) { }

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
    this.profileService.getProfile(3).subscribe(data => {
      //this.listOfData = data;
      this.authService.getById("1").subscribe(data => {
        this.userInfo = data;
        console.log(this.userInfo);
      });
      this.profileService.checkFollowing(1,3).subscribe(data =>{
        this.checkFollowing = data;
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

  showPost(data : any) : void
  {
      this.isVisible = true;
      this.location = data.contentSrcs[0];
      this.likesCount = this.countLikes(data);
      this.dislikesCount = this.countDislikes(data);
      this.commentsCount = this.countComments(data);
      this.comments = data.comments;
      this.currentPostId = data.id;
      this.postStoryService.isItLiked(1,data.id).subscribe(data =>{
        this.liked = data;
      });
      this.postStoryService.isItDisliked(1,data.id).subscribe(data=>{
        this.disliked = data;
      });
      this.postStoryService.isItReported(1,data.id).subscribe(data =>{
        this.reported = data;
      })
  }

  handleCancel() : void
  {
    this.isVisible = false;
    const body = {
      postIds: this.postIds
    }
    this.postStoryService.getPosts(body).subscribe(data => {
      console.log(data);
      this.listOfData = data;
  });
  }

  
  like(): void {
    
    this.postStoryService.like(1,this.currentPostId).subscribe(data=>{
      this.liked = true;
      this.likesCount = this.likesCount + 1;
      if(this.disliked)
        this.dislikesCount = this.dislikesCount - 1;
      this.disliked = false;
            
     
    });


  }

  dislike(): void {
    
    this.postStoryService.dislike(1,this.currentPostId).subscribe(data=>{
      this.disliked = true;
      this.dislikesCount = this.dislikesCount + 1;
      if(this.liked)
        this.likesCount = this.likesCount - 1;
      this.liked = false;
    })

  }

  handleSubmit(): void {
    this.submitting = true;
    const content = this.inputValue;
    this.inputValue = '';

    setTimeout(() => {
      this.submitting = false;
    }, 800);

    const body = {
      postId : this.currentPostId,
      username : "vuk",
      content : content
    }

    this.postStoryService.addComment(body).subscribe(data => {
      this.comments = data.comments;
      this.commentsCount = this.countComments(data);
    });
    
  }

  report() : void
  {
    this.postStoryService.report(1,this.currentPostId).subscribe(data =>{
      this.reported = true;
    });
    
  }

}





