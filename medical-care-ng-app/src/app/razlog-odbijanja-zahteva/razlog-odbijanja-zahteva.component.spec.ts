import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RazlogOdbijanjaZahtevaComponent } from './razlog-odbijanja-zahteva.component';

describe('RazlogOdbijanjaZahtevaComponent', () => {
  let component: RazlogOdbijanjaZahtevaComponent;
  let fixture: ComponentFixture<RazlogOdbijanjaZahtevaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RazlogOdbijanjaZahtevaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RazlogOdbijanjaZahtevaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
