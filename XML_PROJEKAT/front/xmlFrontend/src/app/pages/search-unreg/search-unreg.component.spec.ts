import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchUnregComponent } from './search-unreg.component';

describe('SearchUnregComponent', () => {
  let component: SearchUnregComponent;
  let fixture: ComponentFixture<SearchUnregComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchUnregComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchUnregComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
