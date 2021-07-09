import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-view-favourites',
  templateUrl: './view-favourites.component.html',
  styleUrls: ['./view-favourites.component.css']
})
export class ViewFavouritesComponent implements OnInit {

  constructor(private profileService: ProfileService,private postStoryService: PostStoryService, private authService : AuthService) { }

  posts : any;
  collections : any[] = [];
  currentPosts : any;
  collection = "all";
  all : any;
  decoded_token : any;

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.all = "all";
    this.profileService.getFavourites(this.decoded_token.id).subscribe(data=>{
      const body = 
      {
        postIds : data.postIds
      }
      this.postStoryService.getPosts(body).subscribe(data=>{
        this.posts = data;
        this.currentPosts = this.posts;
      });
      data.collections.forEach((element: any) => {
        const body = 
        {
          postIds : element.postIds
        }
        this.postStoryService.getPosts(body).subscribe(data=>{
            const newCollection = {
              name : element.name,
              posts : data
            }
            this.collections.push(newCollection);
        })
      });
    })
  }

  showCollection(value: { label: string; value: string})
  {
    if(value == this.all)
    {
      this.currentPosts = this.posts;
    }
    else
    {
      this.collections.forEach(element =>{
        if(element.name == value)
          this.currentPosts = element.posts;
      });
    }
    
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
