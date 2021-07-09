
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';


@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {

  validateForm!: FormGroup; 
  image: any;
  file!: File;
  price!: number;
  quantity!: number; 
  decoded_token : any;
  constructor(private productService: ProductService, private fb: FormBuilder, private http: HttpClient,
    private toastr : ToastrService, private authService : AuthService) { }

  fileChange(event: any) {
    // Instantiate an object to read the file content
    let reader = new FileReader();
    // when the load event is fired and the file not empty
    if(event.target.files && event.target.files.length > 0) {
      // Fill file variable with the file content
      this.file = event.target.files[0];
    }
  }

  submitForm() {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.price = this.validateForm.value.price;
    this.quantity = this.validateForm.value.quantity;

    // Instantiate a FormData to store form fields and encode the file
    let body = new FormData();
    // Add file content to prepare the request
    body.append("file", this.file);
    console.log(this.file);
    console.log(this.price);
    body.append("price", this.price.toString());
    body.append("quantity", this.quantity.toString());
    //body.append("agentId", this.decoded_token.id);
    body.append("agentId", "1");
    body.append("name", this.validateForm.value.name);
    // Launch post request
    if(this.validateForm.valid){
      this.productService.createProduct(body)
      .subscribe(
        // Admire results
        (data) => {console.log(data)},
        // Or errors :-(
        error => {console.log(error);  this.toastr.error("Error while creating product!");},
        // tell us if it's finished
        () => { console.log("completed");  this.toastr.success("Product successfully created!"); }
      );
    }

  }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.validateForm = this.fb.group({
      price: [0,[Validators.required]],
      quantity: [0,[Validators.required]],
      name: [null,[Validators.required]],
      file: [null,[Validators.required]]
    });
  }

}