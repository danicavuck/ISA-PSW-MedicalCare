import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DefinisanjePregledaComponent } from './definisanje-pregleda.component';

describe('DefinisanjePregledaComponent', () => {
  let component: DefinisanjePregledaComponent;
  let fixture: ComponentFixture<DefinisanjePregledaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DefinisanjePregledaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DefinisanjePregledaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
