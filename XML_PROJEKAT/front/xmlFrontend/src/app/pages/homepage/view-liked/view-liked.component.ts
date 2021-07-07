import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-view-liked',
  templateUrl: './view-liked.component.html',
  styleUrls: ['./view-liked.component.css']
})


export class ViewLikedComponent implements OnInit {

  listOfData : any;
  liked : any;
  disliked : any;
  radioValue : any;

  constructor(private profileService: ProfileService, private postStoryService: PostStoryService) { }

  ngOnInit(): void {

    this.radioValue = 'liked';

    this.postStoryService.likedByProfile(2).subscribe(data => {

      this.liked = data;
      this.listOfData = this.liked;

    });

    this.postStoryService.dislikedByProfile(2).subscribe(data => {
      this.disliked = data;
    });


    
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

  onChangeStatus()
  {
    console.log(this.radioValue);
    if(this.radioValue != 'liked')
    {
      this.listOfData = this.liked;
    }
    else
    {
      this.listOfData = this.disliked;
    }
  }

  @ViewChild('videoPlayer') 
  videoplayer!: ElementRef;
  toggleVideo() {
      this.videoplayer.nativeElement.play();
  }


}
