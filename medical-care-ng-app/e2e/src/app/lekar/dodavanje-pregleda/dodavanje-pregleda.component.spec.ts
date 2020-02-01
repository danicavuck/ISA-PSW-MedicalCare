import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DodavanjePregledaComponent } from './dodavanje-pregleda.component';

describe('DodavanjePregledaComponent', () => {
  let component: DodavanjePregledaComponent;
  let fixture: ComponentFixture<DodavanjePregledaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DodavanjePregledaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DodavanjePregledaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
