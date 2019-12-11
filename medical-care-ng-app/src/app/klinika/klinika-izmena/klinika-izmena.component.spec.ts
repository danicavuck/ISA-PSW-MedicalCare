import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KlinikaIzmenaComponent } from './klinika-izmena.component';

describe('KlinikaIzmenaComponent', () => {
  let component: KlinikaIzmenaComponent;
  let fixture: ComponentFixture<KlinikaIzmenaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KlinikaIzmenaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KlinikaIzmenaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
