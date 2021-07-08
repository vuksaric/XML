import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from "../../environments/environment";

const product_url = environment.product_url;

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  public createProduct(body: any): Observable<any>{
    return this.http.post(product_url+`/create`,body);
  }

  public viewProducts(agentId: any) : Observable<any>{
    return this.http.get(product_url+`/getByAgent/${agentId}`);
  }
}
