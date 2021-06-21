import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { differenceInCalendarDays } from 'date-fns';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  validateForm!: FormGroup;  

  today = new Date();
  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      surname: [null,[Validators.required]],
      name: [null,[Validators.required]],
      email: [null,[Validators.required]],
      username: [null,[Validators.required]],
      gender: [null,[Validators.required]],
      date: [null,[Validators.required]],
      biography: [null,[Validators.required]],
      website: [null,[Validators.required]],
      privacy: [null,[Validators.required]],
      messages: [null,[Validators.required]],
       tags: [null,[Validators.required]],

    });
  }

  submitForm() {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

  }

  disabledDate = (current: Date): boolean => {
    return differenceInCalendarDays(current, this.today) > 0;
  };

}
