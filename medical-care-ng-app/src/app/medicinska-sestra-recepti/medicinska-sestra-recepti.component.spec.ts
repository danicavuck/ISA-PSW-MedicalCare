import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicinskaSestraReceptiComponent } from './medicinska-sestra-recepti.component';

describe('MedicinskaSestraReceptiComponent', () => {
  let component: MedicinskaSestraReceptiComponent;
  let fixture: ComponentFixture<MedicinskaSestraReceptiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedicinskaSestraReceptiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicinskaSestraReceptiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
