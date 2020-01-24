import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DodajLekComponent } from './dodaj-lek.component';

describe('DodajLekComponent', () => {
  let component: DodajLekComponent;
  let fixture: ComponentFixture<DodajLekComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DodajLekComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DodajLekComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
