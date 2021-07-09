import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const campaign_url = environment.campaign_url;

@Injectable({
  providedIn: 'root'
})
export class CampaignService {

  constructor(private http: HttpClient) { }

  public new(body: any) : Observable<any>{ 
    return this.http.post(campaign_url + `/new`, body);
  }

  public getAll(): Observable<any>{
    return this.http.get(campaign_url+`/getAll`);
  }

  public edit(body: any) : Observable<any>{ 
    return this.http.put(campaign_url + `/edit`, body);
  }

  public delete(body: any) : Observable<any>{ 
    return this.http.put(campaign_url + `/delete`, body);
  }

  public getPostsForProfile(body : any): Observable<any>{
    return this.http.post(campaign_url+`/getPostsForProfile`,body);
  }

  public getStoriesForProfile(body : any): Observable<any>{
    return this.http.post(campaign_url+`/getStoriesForProfile`,body);
  }

}
