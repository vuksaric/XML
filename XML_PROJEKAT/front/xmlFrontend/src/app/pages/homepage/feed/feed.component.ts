import { Component, OnInit } from '@angular/core';
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

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})


export class FeedComponent implements OnInit {

  constructor(private profileService: ProfileService, private postStoryService: PostStoryService) { }
  postIds!: any;
  listOfData : any[] = [];
  inputValue = '';
  submitting = false;
  isVisibleFavourites = false;
  currentPostId : any;
  collections : any;
  collectionName : any;

  ngOnInit(): void {

    this.profileService.getPostIdsFeed(1).subscribe(data => {
      this.postIds = data;

      this.profileService.getCollections(1).subscribe(data=>{
        this.collections = data;
    });

      const body = {
        postIds : this.postIds
      }
      this.postStoryService.getPostsFeed(body).subscribe(data =>{
       
        var liked : boolean;
        var disliked : boolean;
        var reported : boolean;
        var favourite : boolean;
        
        data.forEach((element: any) => {
         
                  var likes = element.likeIds.length;
                  console.log(element);
                  var dislikes = element.dislikeIds.length;
                  var comments = element.comments.length;
                  const data1 = {
                    post : element,
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
    
            });
  }

  liked(post : any) : boolean
  {
    var result : any;
    this.postStoryService.isItLiked(1,post.id).subscribe(data =>{
      return data;
    });
    return false;
  }

  disliked(post : any) : boolean
  {
    var result : any;
    this.postStoryService.isItDisliked(1,post.id).subscribe(data=>{
      return data;
    });
    return result
  }

  reported(post : any) : boolean
  {
    var result : any;
    this.postStoryService.isItReported(1,post.id).subscribe(data=>{
      return data;
    });
    return result
  }

  favourite(post : any) : boolean
  {
    this.profileService.checkFavourite(1, post.id).subscribe(data =>{
      return data;
    });
    return false;
  }

  like(post : any): void {
    var liked : boolean;
    this.postStoryService.isItLiked(1,post.id).subscribe(data =>{
      liked = data;
      if(!liked)
    this.postStoryService.like(1,post.id).subscribe(data=>{
      window.location.reload();
    });
    });
    
    


  }

  dislike(post : any): void {
    var disliked : boolean;
    this.postStoryService.isItDisliked(1,post.id).subscribe(data=>{
      disliked = data;
      if(!disliked)
      {
        this.postStoryService.dislike(1,post.id).subscribe(data=>{
          window.location.reload();
        })
      }
    });
    

  }

  report(post : any) : void
  {
    this.postStoryService.isItReported(1,post.id).subscribe(data=>{
      if(!data)
      {
        this.postStoryService.report(1,this.postIds.id,2).subscribe(data =>{
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
      username : "vuk",
      content : content
    }

    this.postStoryService.addComment(body).subscribe(data => {
      window.location.reload();
    });
    
  }

  favouriteClick(data : any)
  {
    this.profileService.checkFavourite(1, data.post.id).subscribe(data =>{
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
      profileId : 1,
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

  

}
