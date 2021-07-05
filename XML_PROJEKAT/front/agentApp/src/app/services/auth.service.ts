import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from "../../environments/environment";

const auth_url = environment.auth_url;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public registration(body: any) : Observable<any>{ 
    return this.http.post(auth_url + `/registration`, body);
  }
}
