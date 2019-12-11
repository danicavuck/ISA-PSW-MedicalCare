import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminKcComponent } from './admin-kc.component';

describe('AdminKcComponent', () => {
  let component: AdminKcComponent;
  let fixture: ComponentFixture<AdminKcComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminKcComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminKcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
