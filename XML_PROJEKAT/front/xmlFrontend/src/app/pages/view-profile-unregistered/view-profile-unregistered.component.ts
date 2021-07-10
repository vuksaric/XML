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
  selector: 'app-view-profile-unregistered',
  templateUrl: './view-profile-unregistered.component.html',
  styleUrls: ['./view-profile-unregistered.component.css']
})
export class ViewProfileUnregisteredComponent implements OnInit {

  pictureId: any;
  pictureLocations!: any;
  postIds!: any;
  listOfData : any;
  profile : any;
  userInfo : any;
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

  
  suggestions : string[]= [];
  tags: string[]=[];

  constructor(private profileService: ProfileService, private postStoryService: PostStoryService, private imageService: ImageService, 
    private sanitizer: DomSanitizer, private authService : AuthService,private activatedRoute: ActivatedRoute,
     private followRequestService : FollowRequestService, private toastr : ToastrService,private router: Router,private toastrService : ToastrService) { }



  ngOnInit(): void {

    this.profileService.getProfile(this.activatedRoute.snapshot.paramMap.get('username')).subscribe(data => {
      if(data == null)
      {
        this.toastr.error("This profile is currently shut down");
        this.router.navigate(['homepage']);
      }
      this.profile = data;  
      const storyBody = {
        postIds: this.profile.storyIds
      }
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
      this.suggestions = this.suggestions.filter(suggestion => suggestion!=found[found.length-1]?.slice(2));
      if(!this.tags.includes(found[found.length-1]?.slice(2)) && found[found.length-1]?.slice(2)!="")
        this.tags.push(found[found.length-1]?.slice(2));
    }
     
  }
  
}
