import { Component, OnInit } from '@angular/core';
import { VerificationRequestServiceService } from 'src/app/services/verification-request-service.service';

export interface VerificationRequest{
  name: string;
  surname: string;
  category: string;
  officialDocument: File;
}

@Component({
  selector: 'app-view-verification-req',
  templateUrl: './view-verification-req.component.html',
  styleUrls: ['./view-verification-req.component.css']
})
export class ViewVerificationReqComponent implements OnInit {
  listOfData! : VerificationRequest[];
  listOfColumn = [
    {
      title: 'User name',
      compare: (a: VerificationRequest, b: VerificationRequest) => a.name.localeCompare(b.name),
      priority: 0
    },
    {
      title: 'User surname',
      compare: (a: VerificationRequest, b: VerificationRequest) => a.surname.localeCompare(b.surname),
      priority: 0
    },
    {
      title: 'Profile category',
      compare: (a: VerificationRequest, b: VerificationRequest) => a.category.localeCompare(b.surname),
      priority: 2
    },
    {
      title: 'Confirm',
    }
  ];
  constructor(private verificationService: VerificationRequestServiceService) { }

  confirm(): void{

  }

  ngOnInit(): void {
    this.verificationService.viewVerificationRequests().subscribe(data => {
      this.listOfData = data;
      console.log(data);});
  }
}