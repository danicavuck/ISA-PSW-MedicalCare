import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SestraPacijentServiceComponent } from './sestra-pacijent-service.component';

describe('SestraPacijentServiceComponent', () => {
  let component: SestraPacijentServiceComponent;
  let fixture: ComponentFixture<SestraPacijentServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SestraPacijentServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SestraPacijentServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
