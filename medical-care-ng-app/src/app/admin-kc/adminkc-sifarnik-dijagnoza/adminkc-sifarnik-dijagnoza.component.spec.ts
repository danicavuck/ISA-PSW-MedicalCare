import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminkcSifarnikDijagnozaComponent } from './adminkc-sifarnik-dijagnoza.component';

describe('AdminkcSifarnikDijagnozaComponent', () => {
  let component: AdminkcSifarnikDijagnozaComponent;
  let fixture: ComponentFixture<AdminkcSifarnikDijagnozaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminkcSifarnikDijagnozaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminkcSifarnikDijagnozaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
