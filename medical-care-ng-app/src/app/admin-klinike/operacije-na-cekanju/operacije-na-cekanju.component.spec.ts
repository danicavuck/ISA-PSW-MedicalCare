import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OperacijeNaCekanjuComponent } from './operacije-na-cekanju.component';

describe('OperacijeNaCekanjuComponent', () => {
  let component: OperacijeNaCekanjuComponent;
  let fixture: ComponentFixture<OperacijeNaCekanjuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OperacijeNaCekanjuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OperacijeNaCekanjuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
