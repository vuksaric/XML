import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const login_url = environment.login_url;

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private router: Router,private toastr: ToastrService) { }

  public login(body: any) : Observable<any>{ 
    return this.http.post(login_url + `/login`, body);
  }

  public registration(body: any) : Observable<Boolean>{ 
    return this.http.post<Boolean>(login_url + `/registration`, body);
  }
}
