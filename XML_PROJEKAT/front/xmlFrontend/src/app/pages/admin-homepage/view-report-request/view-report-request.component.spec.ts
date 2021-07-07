import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewReportRequestComponent } from './view-report-request.component';

describe('ViewReportRequestComponent', () => {
  let component: ViewReportRequestComponent;
  let fixture: ComponentFixture<ViewReportRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewReportRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewReportRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
