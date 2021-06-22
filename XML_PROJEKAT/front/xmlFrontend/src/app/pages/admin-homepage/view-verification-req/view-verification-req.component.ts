import { Component, OnInit } from '@angular/core';
import { ProfileService } from 'src/app/services/profile.service';
import { VerificationRequestServiceService } from 'src/app/services/verification-request-service.service';
import { ToastrService } from 'ngx-toastr';

export interface VerificationRequest{
  name: string;
  surname: string;
  category: string;
  officialDocument: File;
  profileId: number;
  confirmed: boolean;
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
  constructor(private verificationService: VerificationRequestServiceService, private toastr : ToastrService) { }

  confirm(profileId: number): void{
    this.verificationService.edit(profileId).subscribe(data => {
      console.log(profileId);
      this.toastr.success("Successfully confirmed!!!");
      this.ngOnInit();
      

    })
    
  }

  ngOnInit(): void {
    this.verificationService.viewVerificationRequests().subscribe(data => {
      this.listOfData = data;
      console.log(data);});
  }
}