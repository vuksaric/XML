import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { CampaignService } from 'src/app/services/campaign.service';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';

interface Element{
  post : any,
  liked : any,
  disliked : any,
  reported : any,
  likes : any,
  dislikes : any,
  comments : any,
  favourite : any
}

interface FeedPostRequest{
  postId : number,
  username : String
}

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})


export class FeedComponent implements OnInit {

  constructor(private profileService: ProfileService, private postStoryService: PostStoryService, private authService : AuthService, private campaignService : CampaignService) { }
  postIds: FeedPostRequest[] = [];
  listOfData : any[] = [];
  inputValue = '';
  submitting = false;
  isVisibleFavourites = false;
  currentPostId : any;
  collections : any;
  collectionName : any;
  stories : any[] = [];
  isVisibleStory = false;
  slideIndex = 1;
  decoded_token : any;
  profile : any;
  profileRequest : any;

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.profileService.getProfile(this.decoded_token.username).subscribe(data => {
      this.profile = data;
      this.profileRequest = {
        dateOfBirth : this.profile.dateOfBirth,
        gender : this.profile.gender,
        profileCategory : this.profile.profileCategory
      }
    
    this.profileService.getPostIdsFeed(this.decoded_token.id).subscribe(data => {
      console.log(data);
      this.postIds = data;

      this.profileService.getCollections(this.decoded_token.id).subscribe(data=>{
        this.collections = data;
     });

      this.postStoryService.getPostsFeed(this.postIds).subscribe(data =>{
       
        var liked : boolean;
        var disliked : boolean;
        var reported : boolean;
        var favourite : boolean;
        data.forEach((element: any) => {
         
          var likes = element.likeIds.length;
          console.log(element);
          var dislikes = element.dislikeIds.length;
          var comments = element.comments.length;
          var contentAlbum: { src: any; image: any; display: string; }[] = [];
          element.content.forEach((element: any) => {

            const newContent =
            {
              src : element.src,
              image : element.image,
              display : "none"
            }
            contentAlbum.push(newContent);
          });
          const data1 = {
            post : element,
            slideIndex : 1,
            contentAlbum : contentAlbum,
            liked : liked,
            disliked : disliked,
            reported : reported,
            likes : likes,
            dislikes : dislikes,
            comments : comments,
            favourite : favourite
          }
          this.listOfData.push(data1);
        });
      });

      this.campaignService.getPostsForProfile(this.profileRequest).subscribe(data=>{
        this.postStoryService.getPostCommercials(data).subscribe(data=>{
          var liked : boolean;
          var disliked : boolean;
          var reported : boolean;
          var favourite : boolean;
          data.forEach((element: any) => {
         
            var likes = element.likeIds.length;
            console.log(element);
            var dislikes = element.dislikeIds.length;
            var comments = element.comments.length;
            var contentAlbum: { src: any; image: any; display: string; }[] = [];
            element.content.forEach((element: any) => {
  
              const newContent =
              {
                src : element.src,
                image : element.image,
                display : "none"
              }
              contentAlbum.push(newContent);
            });
            const data1 = {
              post : element,
              slideIndex : 1,
              contentAlbum : contentAlbum,
              liked : liked,
              disliked : disliked,
              reported : reported,
              likes : likes,
              dislikes : dislikes,
              comments : comments,
              favourite : favourite
            }
            this.listOfData.push(data1);
          });
          
        })
      });

      this.listOfData = this.shuffle(this.listOfData);
    
    });

    this.profileService.getStoriesFeed(this.decoded_token.id).subscribe(data=>{
        this.postStoryService.getStoriesFeed(data).subscribe(data=>{
          data.forEach((element: any) => {

            const newStory = {
              story : element,
              display : "none"
            }
  
            this.stories.push(newStory);
          });
        });
    });

    this.campaignService.getStoriesForProfile(this.profileRequest).subscribe(data=>{
      this.postStoryService.getStoryCommercials(data).subscribe(data=>{
        data.forEach((element: any) => {

          const newStory = {
            story : element,
            display : "none"
          }

          this.stories.push(newStory);
        });
      })
    })

    this.stories = this.shuffle(this.stories);
  })
  }
  
  like(post : any): void {
    var liked : boolean;
    this.postStoryService.isItLiked(this.decoded_token.id,post.id).subscribe(data =>{
      liked = data;
      if(!liked)
    this.postStoryService.like(this.decoded_token.id,post.id).subscribe(data=>{
      window.location.reload();
    });
    });
    
    


  }

  dislike(post : any): void {
    var disliked : boolean;
    this.postStoryService.isItDisliked(this.decoded_token.id,post.id).subscribe(data=>{
      disliked = data;
      if(!disliked)
      {
        this.postStoryService.dislike(this.decoded_token.id,post.id).subscribe(data=>{
          window.location.reload();
        })
      }
    });
    

  }

  report(post : any) : void
  {
    this.postStoryService.isItReported(this.decoded_token.id,post.id).subscribe(data=>{
      if(!data)
      {
        this.postStoryService.report(this.decoded_token.id,post.id,post.username).subscribe(data =>{
          window.location.reload();
        });
      }
    })
    
    
  }

  handleSubmit(post : any): void {
    this.submitting = true;
    const content = this.inputValue;
    this.inputValue = '';

    setTimeout(() => {
      this.submitting = false;
    }, 800);

    const body = {
      postId : post.id,
      username : this.decoded_token.username,
      content : content
    }

    this.postStoryService.addComment(body).subscribe(data => {
      window.location.reload();
    });
    
  }

  favouriteClick(data : any)
  {
    this.profileService.checkFavourite(this.decoded_token.id, data.post.id).subscribe(data =>{
      if(!data)
      {
        this.isVisibleFavourites = true;
        this.currentPostId = data.post.id;
      }
    });
     
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
      this.isVisibleFavourites = false;
      window.location.reload();
    })
    
  }

  handleCancelFavourites() : void
  {
    this.isVisibleFavourites = false;
  }

  @ViewChild('videoPlayer') 
  videoplayer!: ElementRef;
  toggleVideo() {
      this.videoplayer.nativeElement.play();
  }

  showStories()
  {
    this.isVisibleStory = true;
    this.slideIndex = 1;
    this.showSlidesStory(this.slideIndex);
  }

  handleCancel()
  {
    this.isVisibleStory = false;
  }

  showSlidesStory(n : any) {
    var i; 
    if (n > this.stories.length) {this.isVisibleStory = false}
    if (n < 1) {this.slideIndex = this.stories.length}
    for (i = 0; i < this.stories.length; i++) {
        this.stories[i].display = "none";
    }
    this.stories[this.slideIndex-1].display = "block";
    if(this.isVisibleStory)
    {
      setTimeout(()=>{                          
        this.showSlidesStory(this.slideIndex += 1);
      }, 5000);
    }
    
  }

  showSlidesAlbum(data : any, n : any) {
    var i; 
    if (n > data.contentAlbum.length) {data.slideIndex = 1}
    if (n < 1) {data.slideIndex = data.contentAlbum.length}
    for (i = 0; i < data.contentAlbum.length; i++) {
        data.contentAlbum[i].display = "none";
    }
    data.contentAlbum[data.slideIndex-1].display = "block";
  }

  plusSlides(data : any, n : any) {
    this.showSlidesAlbum(data, data.slideIndex += n);
  }

  checkAlbum(data : any) : boolean
  {
    if(data.post.content.length > 1)
    {
      this.showSlidesAlbum(data,1);
      return true;
    }
    else
    {
      return false;
    }
  }
  
  shuffle(array : any) {
  var currentIndex = array.length,  randomIndex;

  // While there remain elements to shuffle...
  while (0 !== currentIndex) {

    // Pick a remaining element...
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex--;

    // And swap it with the current element.
    [array[currentIndex], array[randomIndex]] = [
      array[randomIndex], array[currentIndex]];
  }

  return array;
  }

}
