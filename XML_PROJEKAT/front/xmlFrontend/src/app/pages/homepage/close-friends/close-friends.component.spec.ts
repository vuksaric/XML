import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CloseFriendsComponent } from './close-friends.component';

describe('CloseFriendsComponent', () => {
  let component: CloseFriendsComponent;
  let fixture: ComponentFixture<CloseFriendsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CloseFriendsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CloseFriendsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
