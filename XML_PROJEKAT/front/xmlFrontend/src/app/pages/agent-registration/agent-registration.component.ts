import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';
import { AttackService } from 'src/app/services/attack.service';
import { AuthService } from 'src/app/services/auth.service';
import { differenceInCalendarDays } from 'date-fns';
import { RegistrationRequestService } from 'src/app/services/registration-request.service';

@Component({
  selector: 'app-agent-registration',
  templateUrl: './agent-registration.component.html',
  styleUrls: ['./agent-registration.component.css']
})
export class AgentRegistrationComponent implements OnInit {

  selectedValuePhonePrefix = "+381";
  selectedValueGender = "Male";
  //selectedValueDate = null;
  today = new Date();

  errorRegister: boolean = false;
  emailBool: boolean = false;
  nameBool: boolean = false;
  lastNameBool: boolean = false;
  passwordBool: boolean = false;
  streetBool: boolean = false;
  stateBool: boolean = false;
  townBool: boolean = false;
  dateBool: boolean = false;
  phoneBool: boolean = false;
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
  website : string = "";
  dateOfBirth : Date = new Date();
  decoded_token : any;

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
    this.website = this.validateForm.value.website;
    

    this.attackService.email(this.email).subscribe(data => {
      console.log(data);
      this.emailBool = data.bool;
      if(!this.emailBool)
        this.createMessage('email');
      this.attackService.name(this.name).subscribe(data => {

        this.nameBool = data.bool;
        if(!this.nameBool)
          this.createMessage('name');

          this.attackService.name(this.lastname).subscribe(data => {

            this.lastNameBool = data.bool;
            if(!this.lastNameBool)
              this.createMessage('lastname');

              this.attackService.password(this.password).subscribe(data => {

                this.passwordBool = data.bool;
                if(!this.passwordBool)
                  this.createMessagePassword();

                  this.attackService.phoneNumber(this.phone).subscribe(data => {

                    this.phoneBool = data.bool;
                    if(!this.phoneBool)
                      this.createMessagePhone();
                    
                    if(this.emailBool && this.nameBool && this.lastNameBool && this.passwordBool)
                    {
                      const body = {
                        username: this.username,
                        name: this.name,
                        surname: this.lastname,
                        email : this.email,
                        password : this.password,
                        phone : this.selectedValuePhonePrefix + this.phone,
                        dateOfBirth: this.dateOfBirth,
                        gender: this.selectedValueGender,
                        website : this.website,
                        agent : true
                        //userType: "User" //moze biti i systemAdmin
                      }

                      if(this.validateForm.valid){
                        if(this.decoded_token != null)
                        {
                          if(this.decoded_token.username == "admin")
                          {
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
                          else
                          {
                            this.registrationRequestService.newRequest(body).subscribe(data=>{
                              this.toastr.success("You have successfully sent a registrationRequest!!!");
                            })
                          }
                        }
                        else
                        {
                          this.registrationRequestService.newRequest(body).subscribe(data=>{
                            this.toastr.success("You have successfully sent a registrationRequest!!!");
                          })
                        }
  
                      }
                    }
                  });
              });

          });
      });
  
    });
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

  constructor(private fb: FormBuilder,private authService : AuthService, private toastr: ToastrService, private router: Router, private attackService : AttackService, 
    private message: NzMessageService, private registrationRequestService : RegistrationRequestService) { }

  ngOnInit(): void {
    this.decoded_token = this.authService.getDataFromToken();
    this.validateForm = this.fb.group({
      username: [null,[Validators.required]],
      name: [null, [Validators.required]],
      surname: [null, [Validators.required]],
      website: [null, [Validators.required]],
      email: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationValidator]],
      phoneNumberPrefix: ['+381'],
      phone: [null, [Validators.required]],
      dateOfBirth: [null, [Validators.required]]
    });
  }

  createMessage(type : String): void {
    this.message.create("error","Format for" + type + "is nost right");
  }
  
  createMessagePassword(): void {
    this.message.create("error","Format for password is nost right, it needs to have at least 8 characters, one small letter, one big letter, one number and one special charachter");
  }

  createMessagePhone(): void {
    this.message.create("error","Format for phone is nost right, ite needs to be on of te following 123-456-7890, (123) 456-7890, 123 456 7890, 123.456.7890, +91 (123) 456-7890");
  }
  
}
