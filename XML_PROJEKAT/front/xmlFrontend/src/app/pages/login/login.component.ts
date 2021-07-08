import { ToastrService } from 'ngx-toastr';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import jwt_decode from 'jwt-decode';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  validateForm!: FormGroup;
  token: any;
  decoded_token: any;
  errorLogin: boolean = false;

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
  }

  constructor(private fb: FormBuilder, private authService : AuthService, private toastr : ToastrService, private router: Router) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });

  }
  
  login(){
    if(this.validateForm.valid){
      const body = {
        username : this.validateForm.get('username')?.value,
        password : this.validateForm.get('password')?.value
      }

      this.authService.login(body).subscribe( data => {
        this.toastr.success("Logged in");
        if (data === null){
          this.errorLogin = true;
          this.toastr.error("You are not registered!!!");
          this.ngOnInit();
        }else{
          const user = data;
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', JSON.stringify(user.token));
          this.decoded_token = this.getDecodedAccessToken(data.token);
          console.log(this.decoded_token);
          console.log(this.decoded_token.id + " " + this.decoded_token.username);
          console.log(this.decoded_token)
          if(this.decoded_token.username == "admin")
          {
            this.router.navigate(['admin']);
          }
          else
          {
            if(this.decoded_token.agent == true)
              this.router.navigate(['agent']);
            else
            this.router.navigate(['homepage']);
          }
        }
      
      });    
    }
  }

  getDecodedAccessToken(token: string): any {
    try {
      console.log("usao");
      return jwt_decode(token);
    }
    catch (Error) {
      return null;
    }
  }

}


