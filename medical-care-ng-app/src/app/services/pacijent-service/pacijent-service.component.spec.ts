import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PacijentServiceComponent } from './pacijent-service.component';

describe('PacijentServiceComponent', () => {
  let component: PacijentServiceComponent;
  let fixture: ComponentFixture<PacijentServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PacijentServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PacijentServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
