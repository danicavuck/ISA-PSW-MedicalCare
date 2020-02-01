import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LekarNavbarComponent } from './lekar-navbar.component';

describe('LekarNavbarComponent', () => {
  let component: LekarNavbarComponent;
  let fixture: ComponentFixture<LekarNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LekarNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LekarNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
