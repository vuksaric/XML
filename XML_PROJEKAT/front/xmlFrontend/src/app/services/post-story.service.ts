import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const post_url = environment.post_url;
const story_url = environment.story_url;

@Injectable({
  providedIn: 'root'
})
export class PostStoryService {

  constructor(private http: HttpClient) { }

  public createPost(body: FormData): Observable<any>{
    return this.http.post(post_url+'/create',body);
  }

  public creatStory(body: FormData): Observable<any>{
    return this.http.post(story_url+'/create',body);
  }

  public getPosts(body: any): Observable<any>{
    return this.http.post(post_url+`/getPosts`,body);
  }

  public getAllPublic(): Observable<any>{
    return this.http.get(post_url+`/getAllPublic`);
  }
  
  public isItLiked(userId : number, postId : number): Observable<any>{
    return this.http.put(post_url+`/isItLiked/${userId}/${postId}`,null);
  }

  public isItDisliked(userId : number, postId : number): Observable<any>{
    return this.http.put(post_url+`/isItDisliked/${userId}/${postId}`,null);
  }

  public like(userId : number, postId : number): Observable<any>{
    return this.http.put(post_url+`/like/${userId}/${postId}`,null);
  }

  public dislike(userId : number, postId : number): Observable<any>{
    return this.http.put(post_url+`/dislike/${userId}/${postId}`,null);
  }

  public addComment(body: any): Observable<any>{
    return this.http.put(post_url+`/addComment`,body);
  }

  public isItReported(userId : number, postId : number): Observable<any>{
    return this.http.put(post_url+`/isItReported/${userId}/${postId}`,null);
  }

  public report(userId : number, postId : number): Observable<any>{
    return this.http.put(post_url+`/report/${userId}/${postId}`,null);
  }

  public likedByProfile(userId : number): Observable<any>{
    return this.http.get(post_url+`/likedByProfile/${userId}`);
  }

  public dislikedByProfile(userId : number): Observable<any>{
    return this.http.get(post_url+`/dislikedByProfile/${userId}`);
  }
}
