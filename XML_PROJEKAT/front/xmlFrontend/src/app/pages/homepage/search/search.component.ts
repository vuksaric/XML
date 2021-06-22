import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { PostStoryService } from 'src/app/services/post-story.service';

export interface Post{
  location: string;
  caption: string;
}

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})

export class SearchComponent implements OnInit {
  //valedateForm: FormGroup = new FormGroup();
  searchValueProfile: string = "";
  listOfData!: any[];
  locations!: string[];
  searchValueLocation: string = "";
  listOfColumn = [
    {
      title: 'Location',
      compare: (a: Post, b: Post) => a.location.localeCompare(b.location),
      priority: 0
    },
    {
      title: 'Caption',
      compare: (a: Post, b: Post) => a.caption.localeCompare(b.caption),
      priority: 0
    }
  ]
  constructor(private postStoryService: PostStoryService) { }

  search(): void{
    //this.listOfData = this.locations.filter((item: String) => item.toLocaleLowerCase().indexOf(this.searchValueLocation.toLocaleLowerCase()) !== -1);
  }

  ngOnInit(): void {
    this.postStoryService.getAllPublic().subscribe(data =>{
        console.log(data);
        this.listOfData = data;
    });
  }

}
