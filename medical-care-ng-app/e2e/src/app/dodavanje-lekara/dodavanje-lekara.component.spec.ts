import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DodavanjeLekaraComponent } from './dodavanje-lekara.component';

describe('DodavanjeLekaraComponent', () => {
  let component: DodavanjeLekaraComponent;
  let fixture: ComponentFixture<DodavanjeLekaraComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DodavanjeLekaraComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DodavanjeLekaraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
