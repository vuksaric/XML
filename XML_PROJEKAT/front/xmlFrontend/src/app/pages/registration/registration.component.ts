import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { differenceInCalendarDays } from 'date-fns';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  selectedValuePhonePrefix = "+381";
  selectedValueGender = "Male";
  //selectedValueDate = null;
  today = new Date();

  validateForm!: FormGroup;
  username: string = "";
  name: string = "";
  lastname : string = "";
  email : string = "";
  password : string = "";
  street : string = "";
  town : string = "";
  state : string = "";
  phone : string = "";
  dateOfBirth : Date = new Date();

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.username = this.validateForm.value.username;
    this.name = this.validateForm.value.name;
    this.lastname = this.validateForm.value.surname;
    this.email = this.validateForm.value.email;
    this.password = this.validateForm.value.password;
    this.phone = this.validateForm.value.phone;
    this.dateOfBirth = this.validateForm.value.dateOfBirth;

    const body = {
      username: this.username,
      name: this.name,
      surname: this.lastname,
      email : this.email,
      password : this.password,
      phone : this.selectedValuePhonePrefix + this.phone,
      birthday: this.dateOfBirth,
      gender: this.selectedValueGender,   
      //userType: "User" //moze biti i systemAdmin
    }
    if(this.validateForm.valid){
      this.authService.registration(body).subscribe(data => { console.log(data) 
        if(data == true){
          this.toastr.success("You have successfully registered!!!");
          this.router.navigate(['login']);
        }
        else{
          this.toastr.error("Username is not valid!!!");
        }
      })
    }
  }

  disabledDate = (current: Date): boolean => {
    return differenceInCalendarDays(current, this.today) > 0;
  };

  updateConfirmValidator(): void {
    /** wait for refresh value */
    Promise.resolve().then(() => this.validateForm.controls.checkPassword.updateValueAndValidity());
  }

  confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return { required: true };
    } else if (control.value !== this.validateForm.controls.password.value) {
      return { confirm: true, error: true };
    }
    return {};
  };

  constructor(private fb: FormBuilder,private authService : AuthService, private toastr: ToastrService, private router: Router) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null,[Validators.required]],
      name: [null, [Validators.required]],
      surname: [null, [Validators.required]],
      email: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationValidator]],
      phoneNumberPrefix: ['+381'],
      phone: [null, [Validators.required]],
      dateOfBirth: [null, [Validators.required]]
    });
  }
}

