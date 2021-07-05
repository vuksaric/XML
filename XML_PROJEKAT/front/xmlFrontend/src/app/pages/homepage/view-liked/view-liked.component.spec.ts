import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewLikedComponent } from './view-liked.component';

describe('ViewLikedComponent', () => {
  let component: ViewLikedComponent;
  let fixture: ComponentFixture<ViewLikedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewLikedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewLikedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
