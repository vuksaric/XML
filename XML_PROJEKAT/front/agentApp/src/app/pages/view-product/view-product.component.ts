import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-view-product',
  templateUrl: './view-product.component.html',
  styleUrls: ['./view-product.component.css']
})
export class ViewProductComponent implements OnInit {
  id!: any
  imagePath!: string
  name!: string
  price!: string
  isVisible=false
  validateForm!: FormGroup;
  constructor(private productService : ProductService, private activatedRoute: ActivatedRoute, private fb: FormBuilder, 
    private authService : AuthService, private toastr : ToastrService) { }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.paramMap.get('id');
    this.productService.viewProduct(+this.id).subscribe(data=>{
      this.imagePath=data.image;
      this.name=data.name;
      this.price="Price of product: "+data.price+"$";
    });

    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });
  }

  login(){
    this.isVisible = true;
  }

 
  handleOk(): void {
    console.log('Button ok clicked!');
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    if(this.validateForm.valid){
      const body = {
        username : this.validateForm.get('username')?.value,
        password : this.validateForm.get('password')?.value
      }

      this.authService.login(body).subscribe( data => {
        if (data != null) {
          this.toastr.success("Logged in");
          this.productService.buyProduct(+this.id).subscribe(data=>{
            if(data)
              this.toastr.success("Product successfully bought!")
            else
              this.toastr.error("Product out of stock!")
      
          });
        }
        else
          this.toastr.error("Error!!!");
      })
      
      this.isVisible = false;
    }    
   

  }

  handleCancel(): void {
    console.log('Button cancel clicked!');
    this.isVisible = false;
  }

}
