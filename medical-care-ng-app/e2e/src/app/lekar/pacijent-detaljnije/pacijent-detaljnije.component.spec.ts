import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PacijentDetaljnijeComponent } from './pacijent-detaljnije.component';

describe('PacijentDetaljnijeComponent', () => {
  let component: PacijentDetaljnijeComponent;
  let fixture: ComponentFixture<PacijentDetaljnijeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PacijentDetaljnijeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PacijentDetaljnijeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
