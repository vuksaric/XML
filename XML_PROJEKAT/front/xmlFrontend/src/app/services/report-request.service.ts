import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const reportRequest_url = environment.reportRequest_url;

@Injectable({
  providedIn: 'root'
})
export class ReportRequestService {

  constructor(private http: HttpClient) { }

  public getAll(): Observable<any>{
    return this.http.get(reportRequest_url+`/getAll`);
  }

  public process(id : number): Observable<any>{
    return this.http.put(reportRequest_url+`/process/${id}`,null);
  }
}
