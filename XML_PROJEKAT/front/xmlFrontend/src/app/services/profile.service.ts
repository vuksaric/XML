import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const profile_url = environment.profile_url;

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  public newProfile(body: any): Observable<any>{
    return this.http.post(profile_url+`/newProfile`, body);
  }

  public getProfile(body:number): Observable<any>{
    return this.http.get(profile_url+`/get/${body}`);
  }

  public checkFollowing(loggendIn : number, current : number): Observable<any>{
    return this.http.get(profile_url+`/checkFollowing/${loggendIn}/${current}`);
  }

}
