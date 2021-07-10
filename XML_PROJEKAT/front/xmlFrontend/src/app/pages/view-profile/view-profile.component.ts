import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
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

export interface Location{
  location: string;
  display : string
  image : boolean
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
  likesCount : any;
  dislikesCount : any;
  commentsCount : any;
  comments : any;
  likes = 0;
  currentPostId : any;
  liked = false;
  disliked = false;
  favourite = false;
  submitting = false;
  caption : any;
  postLocation : any;
  taggedIds : any;
  inputValue = '';
  data: any[] = [];
  reported = false;
  listOfStories : any[] = [];
  listOfHighlights : any[] = [];
  isVisibleStory = false;
  isVisibleHighlight = false;
  isVisibleFavourites = false;
  collections : any;
  collectionName : any;
  user = {
    author: 'Han Solo',
    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png'
  };
  isVisible = false;
  isVisibleAlbum = false;
  locations: Location[] = [];
  location : any;
  slideIndex = 1;
  friends = false;
  image : any;
  decoded_token : any;

  
  suggestions : string[]= [];
  tags: string[]=[];

  constructor(private profileService: ProfileService, private postStoryService: PostStoryService, private imageService: ImageService, 
    private sanitizer: DomSanitizer, private authService : AuthService,private activatedRoute: ActivatedRoute,
     private followRequestService : FollowRequestService, private toastr : ToastrService,private router: Router,private toastrService : ToastrService) { }



  ngOnInit(): void {

    this.decoded_token = this.authService.getDataFromToken();

    if(this.decoded_token.username == "admin")
    {
        this.toastrService.error("Restricted access");
        this.router.navigate(['admin']);
    }

    this.profileService.getProfile(this.activatedRoute.snapshot.paramMap.get('username')).subscribe(data => {
      if(data == null)
      {
        this.toastr.error("This profile is currently shut down");
        this.router.navigate(['homepage']);
      }
      this.profile = data;
      this.profileService.checkFollowing(this.decoded_token.id,this.profile.id).subscribe(data =>{
        this.checkFollowing = data;
        if(!this.profile.isPrivate || this.checkFollowing)
        {
          
          this.postStoryService.getStories(storyBody).subscribe(data => {
            data.forEach((element: any) => {
              if(element.closeFriends)
              {
                if(this.friends)
                {
                  const newStory = {
                    story : element,
                    display : "none"
                  }
        
                  this.listOfStories.push(newStory);
                }
              }
              else
              {
                const newStory = {
                  story : element,
                  display : "none"
                }
      
                this.listOfStories.push(newStory);
              }
              
              
            });
            
          });
  
          this.postStoryService.getHighlightFeed(storyBody).subscribe(data =>{
            data.forEach((element: any) => {
  
              if(element.closeFriends)
              {
                if(this.friends)
                {
                  const newStory = {
                    story : element,
                    display : "none"
                  }
        
                  this.listOfHighlights.push(newStory);
                }
              }
              else
              {
                const newStory = {
                  story : element,
                  display : "none"
                }
      
                this.listOfHighlights.push(newStory);
              }
              
            });
          });
        }
      });

      this.profileService.checkMuted(this.decoded_token.id,this.profile.id).subscribe(data =>{
        this.checkMuted = data;
      });

      this.profileService.checkBlocked(this.decoded_token.id,this.profile.id).subscribe(data =>{
        this.checkBlocked = data;
      });


      this.followRequestService.checkRequest(this.decoded_token.id,this.profile.id).subscribe(data=>{
        this.checkRequest = data;
      });

      const storyBody = {
        postIds: this.profile.storyIds
      }

      this.profileService.checkCloseFriends(this.decoded_token.id,this.profile.id).subscribe(data=>{
        this.friends = data;
      })
     
     

      this.profileService.getCollections(this.decoded_token.id).subscribe(data=>{
          this.collections = data;
      });

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

    this.profileService.getProfilesForTagging(this.decoded_token.id).subscribe(data=>{console.log(data);
      this.suggestions=data;
    });

      
  }

  @ViewChild('videoPlayer') 
  videoplayer!: ElementRef;
  toggleVideo() {
      this.videoplayer.nativeElement.play();
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
    this.profileService.followProfile(this.decoded_token.id,this.profile.id).subscribe(data =>{
        location.reload();
    });
  }

  unfollow() : void
  {
    this.profileService.unfollowProfile(this.decoded_token.id,this.profile.id).subscribe(data =>{
      location.reload();
    });
  }

  mute() : void
  {
    this.profileService.muteProfile(this.decoded_token.id,this.profile.id).subscribe(data =>{
      location.reload();
    });
  }

  unmute() : void
  {
    this.profileService.unmuteProfile(this.decoded_token.id,this.profile.id).subscribe(data =>{
      location.reload();
    });
  }

  block() : void
  {
    this.profileService.blockProfile(this.decoded_token.id,this.profile.id).subscribe(data =>{
      this.profileService.checkFollowing(this.decoded_token.id,this.profile.id).subscribe(data =>{
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
      fromProfileId : this.decoded_token.id,
      toProfileId : this.profile.id
    }
    this.followRequestService.newRequest(body).subscribe(data =>{
      location.reload();
    })
  }

  showPost(data : any) : void
  {
      if(data.content.length > 1)
      {
        this.isVisibleAlbum = true;

        data.content.forEach((element: any) => {
          const newLocation = {
            location : element.src,
            display : "none",
            image : element.image

          }
          this.locations.push(newLocation);
        });
        this.slideIndex = 1;
        this.showSlides(this.slideIndex);

      }
      else
      {
        this.isVisible = true;
        this.location = data.content[0].src;
        this.image = data.content[0].image;
      }
      this.caption = data.caption;
      this.postLocation = data.location;
      this.likesCount = this.countLikes(data);
      this.dislikesCount = this.countDislikes(data);
      this.commentsCount = this.countComments(data);
      this.comments = data.comments;
      this.currentPostId = data.id;
      this.taggedIds = data.tagged;

      this.postStoryService.isItLiked(this.decoded_token.id,data.id).subscribe(data =>{
        this.liked = data;
      });
      this.postStoryService.isItDisliked(this.decoded_token.id,data.id).subscribe(data=>{
        this.disliked = data;
      });
      this.postStoryService.isItReported(this.decoded_token.id,data.id).subscribe(data =>{
        this.reported = data;
      })

      this.profileService.checkFavourite(this.decoded_token.id, data.id).subscribe(data =>{
        this.favourite = data;
      })
  }

  showStory()
  {
      this.isVisibleStory = true;
      this.slideIndex = 1;
      this.showSlidesStory(this.slideIndex);

  }

  showHighlights()
  {
    this.isVisibleHighlight = true;
    this.slideIndex = 1;
    this.showSlidesHighlight(this.slideIndex);
  }


  handleCancel() : void
  {
    this.isVisible = false;
    this.isVisibleAlbum = false;
    this.isVisibleStory = false;
    this.isVisibleHighlight = false;
    
    const body = {
      postIds: this.postIds
    }
    this.postStoryService.getPosts(body).subscribe(data => {
      console.log(data);
      this.listOfData = data;
  });
  }

  handleCancelFavourites() : void
  {
    this.isVisibleFavourites = false;
  }

  
  like(): void {
    
    this.postStoryService.like(this.decoded_token.id,this.currentPostId).subscribe(data=>{
      this.liked = true;
      this.likesCount = this.likesCount + 1;
      if(this.disliked)
        this.dislikesCount = this.dislikesCount - 1;
      this.disliked = false;
            
     
    });


  }

  dislike(): void {
    
    this.postStoryService.dislike(this.decoded_token.id,this.currentPostId).subscribe(data=>{
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
      username : this.decoded_token.username,
      content : content,
      taggedUsernames : this.tags
    }

    
   this.postStoryService.addComment(body).subscribe(data => {
      this.comments = data.comments;
      this.commentsCount = this.countComments(data);
      this.tags = [];
      this.profileService.getProfilesForTagging(this.decoded_token.id).subscribe(data=>{console.log(data);
        this.suggestions=data;
      });
    });

  
    
  }

  report() : void
  {
    this.postStoryService.report(this.decoded_token.id,this.currentPostId,this.profile.username).subscribe(data =>{
      this.reported = true;
    });
    
  }

   showSlides(n : any) {
    var i; 
    if (n > this.locations.length) {this.slideIndex = 1}
    if (n < 1) {this.slideIndex = this.locations.length}
    for (i = 0; i < this.locations.length; i++) {
        this.locations[i].display = "none";
    }
    this.locations[this.slideIndex-1].display = "block";
    /*for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }*/
    //dots[this.slideIndex-1].className += " active";
  }

  // Next/previous controls
  plusSlides(n : any) {
  this.showSlides(this.slideIndex += n);
  }

  showSlidesStory(n : any) {
    var i; 
    if (n > this.listOfStories.length) {this.isVisibleStory = false}
    if (n < 1) {this.slideIndex = this.listOfStories.length}
    for (i = 0; i < this.listOfStories.length; i++) {
        this.listOfStories[i].display = "none";
    }
    this.listOfStories[this.slideIndex-1].display = "block";
    if(this.isVisibleStory)
    {
      setTimeout(()=>{                          
        this.showSlidesStory(this.slideIndex += 1);
      }, 5000);
    }
    
  }

  showSlidesHighlight(n : any)
  {
    var i; 
    if (n > this.listOfHighlights.length) {this.isVisibleHighlight = false}
    if (n < 1) {this.slideIndex = this.listOfHighlights.length}
    for (i = 0; i < this.listOfHighlights.length; i++) {
        this.listOfHighlights[i].display = "none";
    }
    this.listOfHighlights[this.slideIndex-1].display = "block";
    if(this.isVisibleHighlight)
    {
      setTimeout(()=>{                          
        this.showSlidesHighlight(this.slideIndex += 1);
      }, 5000);
    }
  }

  favouriteClick()
  {
      this.isVisibleFavourites = true;
  }

  addFavourite(collectionName : any, collection : boolean)
  {
    const body = {
      profileId : this.decoded_token.id,
      postId : this.currentPostId,
      collectionName : collectionName,
      collection : collection
    }

    this.profileService.addFavourite(body).subscribe(data =>{
      this.favourite = true;
      this.isVisibleFavourites = false;
    })
    
  }

  getRegExp(prefix: string | string[]): RegExp {
    const prefixArray = Array.isArray(prefix) ? prefix : [prefix];
    let prefixToken = prefixArray.join('').replace(/(\$|\^)/g, '\\$1');

    if (prefixArray.length > 1) {
      prefixToken = `[${prefixToken}]`;
    }

    return new RegExp(`(\\s|^)(${prefixToken})[^\\s]*`, 'g');
  }


  checkInput(): void{
    console.log(this.inputValue);
    const regex = this.getRegExp('@');
    const found = this.inputValue.match(regex);
    console.log(found);
    if(found!=null){
      this.suggestions = this.suggestions.filter(suggestion => suggestion!=found[found.length-1]?.slice(1));
      if(!this.tags.includes(found[found.length-1]?.slice(1)) && found[found.length-1]?.slice(1)!="")
        this.tags.push(found[found.length-1]?.slice(1));
    }
     
  }
  

}





