import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedSestraDetaljnijeComponent } from './med-sestra-detaljnije.component';

describe('MedSestraDetaljnijeComponent', () => {
  let component: MedSestraDetaljnijeComponent;
  let fixture: ComponentFixture<MedSestraDetaljnijeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedSestraDetaljnijeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedSestraDetaljnijeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
