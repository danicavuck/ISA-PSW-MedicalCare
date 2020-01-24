import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LekServiceComponent } from './lek-service.component';

describe('LekServiceComponent', () => {
  let component: LekServiceComponent;
  let fixture: ComponentFixture<LekServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LekServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LekServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
