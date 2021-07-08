import { Component, OnInit } from '@angular/core';
import { RegistrationRequestService } from 'src/app/services/registration-request.service';

@Component({
  selector: 'app-view-registration-requests',
  templateUrl: './view-registration-requests.component.html',
  styleUrls: ['./view-registration-requests.component.css']
})
export class ViewRegistrationRequestsComponent implements OnInit {

  listOfData : any;
  constructor(private registrationRequestService : RegistrationRequestService) { }

  ngOnInit(): void {
    this.registrationRequestService.getAll().subscribe(data=>{
      this.listOfData = data;
    })
  }

  approve(id : any)
  {
    this.registrationRequestService.approve(id).subscribe(data=>{
      window.location.reload();
    })
  }

  deny(id : any)
  {
    this.registrationRequestService.delete(id).subscribe(data=>{
        window.location.reload();
    })
  }

}
