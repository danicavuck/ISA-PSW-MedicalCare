import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedSestraNavbarComponent } from './med-sestra-navbar.component';

describe('MedSestraNavbarComponent', () => {
  let component: MedSestraNavbarComponent;
  let fixture: ComponentFixture<MedSestraNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedSestraNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedSestraNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
