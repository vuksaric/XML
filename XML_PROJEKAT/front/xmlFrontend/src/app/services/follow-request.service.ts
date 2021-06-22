import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const followRequest_url = environment.followRequest_url;

@Injectable({
  providedIn: 'root'
})
export class FollowRequestService {

  constructor(private http: HttpClient) { }

  public newRequest(body: any): Observable<any>{
    return this.http.post(followRequest_url+`/new`, body);
  }

  public checkRequest(loggendIn : number, current : number): Observable<any>{
    return this.http.get(followRequest_url+`/checkRequest/${loggendIn}/${current}`);
  }

  public getAllForProfile(loggendIn : number): Observable<any>{
    return this.http.get(followRequest_url+`/getAllForProfile/${loggendIn}`);
  }

}
