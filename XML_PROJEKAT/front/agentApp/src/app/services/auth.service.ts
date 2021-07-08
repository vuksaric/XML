import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from "../../environments/environment";

import jwt_decode from 'jwt-decode';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

const auth_url = environment.auth_url;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public registration(body: any) : Observable<any>{ 
    return this.http.post(auth_url + `/registration`, body);
  }

  public login(body: any) : Observable<any>{ 
    return this.http.post(auth_url + `/login`, body);
  }

  public getDataFromToken() : any
  {
    let token : any;
    let decoded_token : any;
    let result : any;
    token = localStorage.getItem("token");
    decoded_token = this.getDecodedAccessToken(token);
    return decoded_token;
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    }
    catch (Error) {
      return null;
    }
  }
}
