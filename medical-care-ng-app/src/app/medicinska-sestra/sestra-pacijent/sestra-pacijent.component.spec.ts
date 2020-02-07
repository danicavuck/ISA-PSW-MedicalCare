import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SestraPacijentComponent } from './sestra-pacijent.component';

describe('SestraPacijentComponent', () => {
  let component: SestraPacijentComponent;
  let fixture: ComponentFixture<SestraPacijentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SestraPacijentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SestraPacijentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
