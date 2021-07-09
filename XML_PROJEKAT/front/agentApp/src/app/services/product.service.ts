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
  public editProduct(body: any): Observable<any>{
    return this.http.put(product_url+`/edit`,body);
  }

  public editProductPicture(body: any): Observable<any>{
    return this.http.put(product_url+`/editPicture`,body);
  }

  public deleteProduct(productId : number): Observable<any>{
    return this.http.delete(product_url+`/delete/${productId}`);
  }

  public viewProduct(productId: any) : Observable<any>{
    return this.http.get(product_url+`/getById/${productId}`);
  }

  public buyProduct(productId: number): Observable<any>{
    return this.http.put(product_url+`/buyProduct/${productId}`,null);
  }
}
