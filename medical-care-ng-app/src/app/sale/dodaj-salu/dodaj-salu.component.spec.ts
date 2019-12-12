import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DodajSaluComponent } from './dodaj-salu.component';

describe('DodajSaluComponent', () => {
  let component: DodajSaluComponent;
  let fixture: ComponentFixture<DodajSaluComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DodajSaluComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DodajSaluComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
