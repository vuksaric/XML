import { Component, OnInit } from '@angular/core';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  constructor(private profileService: ProfileService, private postStoryService: PostStoryService) { }
  postIds!: any;
  listOfData : any[] = [];
  return = false;
  inputValue = '';
  submitting = false;

  ngOnInit(): void {

    this.profileService.getPostIdsFeed(1).subscribe(data => {
      this.postIds = data;
      const body = {
        postIds : this.postIds
      }
      this.postStoryService.getPostsFeed(body).subscribe(data =>{
        data.forEach((element: any) => {
          var liked = this.liked(element);
          var disliked = this.disliked(element);
          var reported = this.reported(element);
          var likes = element.likeIds.length;
          console.log(element);
          var dislikes = element.dislikeIds.length;
          var comments = element.comments.length;
          const data = {
            post : element,
            liked : liked,
            disliked : disliked,
            reported : reported,
            likes : likes,
            dislikes : dislikes,
            comments : comments
          }
          this.listOfData.push(data);
          
        });
      })
    });
  }

  liked(post : any) : boolean
  {
    this.postStoryService.isItLiked(1,post.id).subscribe(data =>{
      this.return = data;
    });
    return this.return
  }

  disliked(post : any) : boolean
  {
    this.postStoryService.isItDisliked(1,post.id).subscribe(data=>{
      this.return = data;
    });
    return this.return
  }

  reported(post : any) : boolean
  {
    this.postStoryService.isItReported(1,post.id).subscribe(data=>{
      this.return = data;
    });
    return this.return
  }

  like(post : any): void {
    
    this.postStoryService.like(1,post.id).subscribe(data=>{
      /*this.liked = true;
      this.likesCount = this.likesCount + 1;
      if(this.disliked)
        this.dislikesCount = this.dislikesCount - 1;
      this.disliked = false;*/
      window.location.reload();
            
     
    });


  }

  dislike(post : any): void {
    
    this.postStoryService.dislike(1,post.id).subscribe(data=>{
      /*this.disliked = true;
      this.dislikesCount = this.dislikesCount + 1;
      if(this.liked)
        this.likesCount = this.likesCount - 1;
      this.liked = false;*/
      window.location.reload();
    })

  }

  report(post : any) : void
  {
    this.postStoryService.report(1,this.postIds.id).subscribe(data =>{
      window.location.reload();
    });
    
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

  

}
