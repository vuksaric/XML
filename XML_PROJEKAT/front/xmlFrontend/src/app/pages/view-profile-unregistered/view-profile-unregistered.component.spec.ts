import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewProfileUnregisteredComponent } from './view-profile-unregistered.component';

describe('ViewProfileUnregisteredComponent', () => {
  let component: ViewProfileUnregisteredComponent;
  let fixture: ComponentFixture<ViewProfileUnregisteredComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewProfileUnregisteredComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewProfileUnregisteredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
