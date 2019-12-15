import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DodavanjeTipaPregledaComponent } from './dodavanje-tipa-pregleda.component';

describe('DodavanjeTipaPregledaComponent', () => {
  let component: DodavanjeTipaPregledaComponent;
  let fixture: ComponentFixture<DodavanjeTipaPregledaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DodavanjeTipaPregledaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DodavanjeTipaPregledaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
