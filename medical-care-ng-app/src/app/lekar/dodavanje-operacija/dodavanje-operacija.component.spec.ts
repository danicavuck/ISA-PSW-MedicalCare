import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DodavanjeOperacijaComponent } from './dodavanje-operacija.component';

describe('DodavanjeOperacijaComponent', () => {
  let component: DodavanjeOperacijaComponent;
  let fixture: ComponentFixture<DodavanjeOperacijaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DodavanjeOperacijaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DodavanjeOperacijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
