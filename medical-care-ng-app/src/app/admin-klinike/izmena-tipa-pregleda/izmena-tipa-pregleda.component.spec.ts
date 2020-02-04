import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IzmenaTipaPregledaComponent } from './izmena-tipa-pregleda.component';

describe('IzmenaTipaPregledaComponent', () => {
  let component: IzmenaTipaPregledaComponent;
  let fixture: ComponentFixture<IzmenaTipaPregledaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IzmenaTipaPregledaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IzmenaTipaPregledaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
