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

  public getProfile(body:any): Observable<any>{
    return this.http.get(profile_url+`/get/${body}`);
  }

  public checkFollowing(loggendIn : number, current : number): Observable<any>{
    return this.http.get(profile_url+`/checkFollowing/${loggendIn}/${current}`);
  }

  public checkMuted(loggendIn : number, current : number): Observable<any>{
    return this.http.get(profile_url+`/checkMuted/${loggendIn}/${current}`);
  }

  public checkBlocked(loggendIn : number, current : number): Observable<any>{
    return this.http.get(profile_url+`/checkBlocked/${loggendIn}/${current}`);
  }

  public getProfile2(body:number): Observable<any>{
    return this.http.get(profile_url+`/getProfile/${body}`);
  }

  public editProfile(body: any): Observable<any>{
    return this.http.put(profile_url+`/editProfile`, body);
  }
  public followProfile(loggendIn : number, current : number): Observable<any>{
    return this.http.put(profile_url+`/follow/${loggendIn}/${current}`,null);
  }

  public unfollowProfile(loggendIn : number, current : number): Observable<any>{
    return this.http.put(profile_url+`/unfollow/${loggendIn}/${current}`,null);
  }

  public muteProfile(loggendIn : number, current : number): Observable<any>{
    return this.http.put(profile_url+`/mute/${loggendIn}/${current}`,null);
  }

  public unmuteProfile(loggendIn : number, current : number): Observable<any>{
    return this.http.put(profile_url+`/unmute/${loggendIn}/${current}`,null);
  }

  public blockProfile(loggendIn : number, current : number): Observable<any>{
    return this.http.put(profile_url+`/block/${loggendIn}/${current}`,null);
  }

  public acceptFollowRequest(to : number, from : number): Observable<any>{
    return this.http.put(profile_url+`/acceptFollowRequest/${to}/${from}`,null);
  }

  public denyFollowRequest(to : number, from : number): Observable<any>{
    return this.http.put(profile_url+`/denyFollowRequest/${to}/${from}`,null);
  }

  public getProfilesForTagging(userInfoId: number) : Observable<any[]>{
    return this.http.get<any[]>(profile_url+`/profilesForTagging/${userInfoId}`);
  }

  public getPublicProfiles() : Observable<any[]>{
    return this.http.get<any[]>(profile_url+`/getPublicProfiles`);
  }

  public getCloseFriends(userInfoId : number) : Observable<any[]>{
    return this.http.get<any[]>(profile_url+`/getCloseFriends/${userInfoId}`);
  }
  public getProfilesForCloseFriends(userInfoId : number) : Observable<any[]>{
    return this.http.get<any[]>(profile_url+`/getProfilesForCloseFriends/${userInfoId}`);
  }
  public addCloseFriend(loggedInId : number, closeFriend: string) : Observable<any[]>{
    return this.http.put<any[]>(profile_url+`/addCloseFriend/${loggedInId}/${closeFriend}`, null);
  }

  public removeCloseFriend(loggedInId : number, closeFriend: string) : Observable<any[]>{
    return this.http.put<any[]>(profile_url+`/removeCloseFriend/${loggedInId}/${closeFriend}`, null);
  }

  public getFollowingProfiles(userInfoId : number) : Observable<any[]>{
    return this.http.get<any[]>(profile_url+`/getFollowingProfiles/${userInfoId}`);
  }

  public getProfilesForSettings(userInfoId : number) : Observable<any[]>{
    return this.http.get<any[]>(profile_url+`/getProfilesForSettings/${userInfoId}`);
  }
  public checkMutedPosts(loggendIn : number, current : string): Observable<any>{
    return this.http.get(profile_url+`/checkMutedPosts/${loggendIn}/${current}`);
  }

  public mutePost(loggendIn : number, current : string): Observable<any>{
    return this.http.put(profile_url+`/mutePost/${loggendIn}/${current}`,null);
  }

  public unmutePost(loggendIn : number, current : string): Observable<any>{
    return this.http.put(profile_url+`/unmutePost/${loggendIn}/${current}`,null);
  }
  public muteStory(loggendIn : number, current : string): Observable<any>{
    return this.http.put(profile_url+`/muteStory/${loggendIn}/${current}`,null);
  }

  public unmuteStory(loggendIn : number, current : string): Observable<any>{
    return this.http.put(profile_url+`/unmuteStory/${loggendIn}/${current}`,null);
  }
  public muteComment(loggendIn : number, current : string): Observable<any>{
    return this.http.put(profile_url+`/muteComment/${loggendIn}/${current}`,null);
  }

  public unmuteComment(loggendIn : number, current : string): Observable<any>{
    return this.http.put(profile_url+`/unmuteComment/${loggendIn}/${current}`,null);
  }
  public muteMessage(loggendIn : number, current : string): Observable<any>{
    return this.http.put(profile_url+`/muteMessage/${loggendIn}/${current}`,null);
  }

  public unmuteMessage(loggendIn : number, current : string): Observable<any>{
    return this.http.put(profile_url+`/unmuteMessage/${loggendIn}/${current}`,null);
  }
  
}
