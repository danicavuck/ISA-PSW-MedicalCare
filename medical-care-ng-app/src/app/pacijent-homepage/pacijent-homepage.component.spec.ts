import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PacijentHomepageComponent } from './pacijent-homepage.component';

describe('PacijentHomepageComponent', () => {
  let component: PacijentHomepageComponent;
  let fixture: ComponentFixture<PacijentHomepageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PacijentHomepageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PacijentHomepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
