import { Component, OnInit } from '@angular/core';
import { PostStoryService } from 'src/app/services/post-story.service';
import { ProfileService } from 'src/app/services/profile.service';
import { ReportRequestService } from 'src/app/services/report-request.service';

@Component({
  selector: 'app-view-report-request',
  templateUrl: './view-report-request.component.html',
  styleUrls: ['./view-report-request.component.css']
})
export class ViewReportRequestComponent implements OnInit {

  listOfData : any;

  constructor(private reportRequestService : ReportRequestService,private profileService: ProfileService, private postStoryService: PostStoryService) { }

  ngOnInit(): void {

    this.reportRequestService.getAll().subscribe(data=>{
      this.listOfData = data;
    })

  }

  remove(postId : any, id : any, username : any)
  {
    this.postStoryService.remove(postId,username).subscribe(data=>{
      this.reportRequestService.process(id).subscribe(data =>
        {
          window.location.reload();
        })
    })
  }

  shutDown(username : any, id : any)
  {
    this.profileService.shutDown(username).subscribe(data=>{
      this.reportRequestService.process(id).subscribe(data =>
        {
          window.location.reload();
        })

    })
  }

  process(id : any)
  {
    this.reportRequestService.process(id).subscribe(data =>
      {
        window.location.reload();
      })
  }

}
