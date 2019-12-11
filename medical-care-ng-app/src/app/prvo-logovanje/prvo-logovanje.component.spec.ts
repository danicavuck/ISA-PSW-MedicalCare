import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrvoLogovanjeComponent } from './prvo-logovanje.component';

describe('PrvoLogovanjeComponent', () => {
  let component: PrvoLogovanjeComponent;
  let fixture: ComponentFixture<PrvoLogovanjeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrvoLogovanjeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrvoLogovanjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
