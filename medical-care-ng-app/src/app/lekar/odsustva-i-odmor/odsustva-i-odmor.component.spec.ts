import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OdsustvaIOdmorComponent } from './odsustva-i-odmor.component';

describe('OdsustvaIOdmorComponent', () => {
  let component: OdsustvaIOdmorComponent;
  let fixture: ComponentFixture<OdsustvaIOdmorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OdsustvaIOdmorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OdsustvaIOdmorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
