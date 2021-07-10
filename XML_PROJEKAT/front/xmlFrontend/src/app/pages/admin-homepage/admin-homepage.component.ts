import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-admin-homepage',
  templateUrl: './admin-homepage.component.html',
  styleUrls: ['./admin-homepage.component.css']
})
export class AdminHomepageComponent implements OnInit {

  isCollapsed = false;
  userID: number = 0;
  decoded_token : any;

  constructor(private router: Router, private authService : AuthService, private toastrService : ToastrService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    if(this.decoded_token== null)
    {
        this.toastrService.error("Restricted access");
        this.router.navigate(['login']);
    }
    if(this.decoded_token.username != "admin")
    {
        this.toastrService.error("Restricted access");
        this.router.navigate(['login']);
    }
  }

  logout(){
    //ovde obrisati sve sto se tice tokena
    localStorage.clear();
    this.router.navigate(['login']);
  }

  ViewVerificationRequest(){
    this.router.navigate(['agent/createProduct']);
  }

  ViewReportRequest(){
    this.router.navigate(['admin/view-report-request']);
  }

  RegisterAgent(){
    this.router.navigate(['admin/register-agent']);
  }

  RegisterRequests()
  {
    this.router.navigate(['admin/view-registration-requests']);
  }

  Logout()
  {
    localStorage.clear();
    this.router.navigate(['login']);
  }

}
