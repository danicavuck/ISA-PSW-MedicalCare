import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrihodComponent } from './prihod.component';

describe('PrihodComponent', () => {
  let component: PrihodComponent;
  let fixture: ComponentFixture<PrihodComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrihodComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrihodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
