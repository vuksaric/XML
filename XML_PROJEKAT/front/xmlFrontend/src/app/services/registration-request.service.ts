import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const registrationRequest_url = environment.registrationRequest_url;

@Injectable({
  providedIn: 'root'
})
export class RegistrationRequestService {

  constructor(private http: HttpClient) { }

  public newRequest(body: any): Observable<any>{
    return this.http.post(registrationRequest_url+`/new`, body);
  }

  public getAll(): Observable<any>{
    return this.http.get(registrationRequest_url+`/getAll`);
  }

  public delete(id: any): Observable<any>{
    return this.http.put(registrationRequest_url+`/delete/${id}`, null);
  }

  public approve(id: any): Observable<any>{
    return this.http.put(registrationRequest_url+`/approve/${id}`, null);
  }
}
