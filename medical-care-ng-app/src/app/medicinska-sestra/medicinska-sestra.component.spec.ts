import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicinskaSestraComponent } from './medicinska-sestra.component';

describe('MedicinskaSestraComponent', () => {
  let component: MedicinskaSestraComponent;
  let fixture: ComponentFixture<MedicinskaSestraComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedicinskaSestraComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicinskaSestraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
