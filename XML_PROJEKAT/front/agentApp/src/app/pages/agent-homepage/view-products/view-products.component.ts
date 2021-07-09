import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NzModalService } from 'ng-zorro-antd/modal';
import { ToastrService } from 'ngx-toastr';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-view-products',
  templateUrl: './view-products.component.html',
  styleUrls: ['./view-products.component.css']
})
export class ViewProductsComponent implements OnInit {
  listOfData :any[]= [];
  isVisible=false;
  file!: File;
  isVisibleEdit=false;
  imagePath! : string;
  validateForm =new FormGroup({
    price : new FormControl(),
    quantity : new FormControl()
  }
  );
  validateFormPicture = new FormGroup({
    file : new FormControl()
  }
  );
  isVisibleEditPicture = false; 
  productId! : number;

  constructor(private productService: ProductService,private fb: FormBuilder,
    private toastr : ToastrService,private modal: NzModalService ) { }

  ngOnInit(): void {
    this.productService.viewProducts(1).subscribe(data=>{console.log(data); this.listOfData=data;})
  }

  viewPicture(path: string): void{
   console.log(path);
   this.imagePath =  path;
   console.log(this.imagePath);
   //var b = document.getElementById("img");
   // b!.setAttribute("src", path);
   this.isVisible=true;
  }
  handleOk(): void {
    console.log('Button ok clicked!');
    this.isVisible = false;
  }

  handleCancel(): void {
    console.log('Button cancel clicked!');
    this.isVisible = false;
  }
  handleCancelEdit(): void {
    console.log('Button cancel clicked!');
    this.isVisibleEdit = false;
  }

  handleOkEdit(){
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    var body ={
      price : this.validateForm.value.price,
      quantity : this.validateForm.value.quantity,
      name :this.validateForm.value.name,
      id : this.productId
    }

    if(this.validateForm.valid){
      this.productService.editProduct(body).subscribe(data=>{ this.toastr.success("Successfully edited product!");
      this.isVisibleEdit=false;this.ngOnInit();
      })
    }
    
  }

  edit(data : any){
    this.isVisibleEdit=true;
    this.validateForm = this.fb.group({
      price: [data.price,[Validators.required]],
      quantity: [data.quantity,[Validators.required]],
      name :[data.name,[Validators.required]],
    });
    this.productId = data.id;
  }
  fileChange(event: any) {
    // Instantiate an object to read the file content
    let reader = new FileReader();
    // when the load event is fired and the file not empty
    if(event.target.files && event.target.files.length > 0) {
      // Fill file variable with the file content
      this.file = event.target.files[0];
    }
  }

  editPicture(data : any){
    this.isVisibleEditPicture=true;
    this.validateFormPicture = this.fb.group({
      file: [null,[Validators.required]]
    });
    this.productId=data.id;
  }

  handleCancelEditPicture(): void {
    console.log('Button cancel clicked!');
    this.isVisibleEditPicture = false;
  }

  handleOkEditPicture(){
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    let body = new FormData();
    body.append("file", this.file);
    console.log(this.file);
    body.append("productId", this.productId.toString());

    if(this.validateForm.valid){
      this.productService.editProductPicture(body)
      .subscribe(
        // Admire results
        (data) => {console.log(data)},
        // Or errors :-(
        error => {console.log(error);  this.toastr.error("Error while editing product picture!");},
        // tell us if it's finished
        () => { console.log("completed");  this.toastr.success("Product picture successfully edited!"); this.isVisibleEditPicture=false;this.ngOnInit(); }
      );
    }
  }

  showDeleteConfirm(data: any): void {
    this.modal.confirm({
      nzTitle: 'Delete product',
      nzContent: '<b style="color: red;">When clicked the OK button, this product will be deleted</b>',
      nzOkText: 'Yes',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzOnOk: () => {
        console.log('OK'); 
        this.productService.deleteProduct(data.id).subscribe(data=>{this.toastr.success("Product successfully deleted!");
          this.ngOnInit();
          });
        },
      nzCancelText: 'No',
      nzOnCancel: () => console.log('Cancel')
    });
  }
}
