import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminkcSifarnikLekovaComponent } from './adminkc-sifarnik-lekova.component';

describe('AdminkcSifarnikLekovaComponent', () => {
  let component: AdminkcSifarnikLekovaComponent;
  let fixture: ComponentFixture<AdminkcSifarnikLekovaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminkcSifarnikLekovaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminkcSifarnikLekovaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
