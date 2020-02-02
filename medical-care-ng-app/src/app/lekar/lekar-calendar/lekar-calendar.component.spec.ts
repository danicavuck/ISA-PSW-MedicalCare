import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LekarCalendarComponent } from './lekar-calendar.component';

describe('LekarCalendarComponent', () => {
  let component: LekarCalendarComponent;
  let fixture: ComponentFixture<LekarCalendarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LekarCalendarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LekarCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
