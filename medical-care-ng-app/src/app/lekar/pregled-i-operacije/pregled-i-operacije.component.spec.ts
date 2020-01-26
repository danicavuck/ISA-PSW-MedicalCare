import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PregledIOperacijeComponent } from './pregled-i-operacije.component';

describe('PregledIOperacijeComponent', () => {
  let component: PregledIOperacijeComponent;
  let fixture: ComponentFixture<PregledIOperacijeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PregledIOperacijeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PregledIOperacijeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
