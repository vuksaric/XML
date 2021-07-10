import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const commercial_url = environment.commercial_url;

@Injectable({
  providedIn: 'root'
})
export class CommercialService {

  constructor(private http: HttpClient) { }

  
  public new(body : any) : Observable<any>
  { 
    return this.http.post(commercial_url + `/new`, body);
  }
}
