import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrisanjeOdsustvaComponent } from './brisanje-odsustva.component';

describe('BrisanjeOdsustvaComponent', () => {
  let component: BrisanjeOdsustvaComponent;
  let fixture: ComponentFixture<BrisanjeOdsustvaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrisanjeOdsustvaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrisanjeOdsustvaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
