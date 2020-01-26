import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KlinikaDetaljnijeComponent } from './klinika-detaljnije.component';

describe('KlinikaDetaljnijeComponent', () => {
  let component: KlinikaDetaljnijeComponent;
  let fixture: ComponentFixture<KlinikaDetaljnijeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KlinikaDetaljnijeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KlinikaDetaljnijeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
