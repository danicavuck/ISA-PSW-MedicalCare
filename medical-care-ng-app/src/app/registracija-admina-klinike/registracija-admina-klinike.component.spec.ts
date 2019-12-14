import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistracijaAdminaKlinikeComponent } from './registracija-admina-klinike.component';

describe('RegistracijaAdminaKlinikeComponent', () => {
  let component: RegistracijaAdminaKlinikeComponent;
  let fixture: ComponentFixture<RegistracijaAdminaKlinikeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistracijaAdminaKlinikeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistracijaAdminaKlinikeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
