import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RazlogOdbijanjaServiceComponent } from './razlog-odbijanja-service.component';

describe('RazlogOdbijanjaServiceComponent', () => {
  let component: RazlogOdbijanjaServiceComponent;
  let fixture: ComponentFixture<RazlogOdbijanjaServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RazlogOdbijanjaServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RazlogOdbijanjaServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
