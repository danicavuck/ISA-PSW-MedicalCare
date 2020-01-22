import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreglediNaCekanjuComponent } from './pregledi-na-cekanju.component';

describe('PreglediNaCekanjuComponent', () => {
  let component: PreglediNaCekanjuComponent;
  let fixture: ComponentFixture<PreglediNaCekanjuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreglediNaCekanjuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreglediNaCekanjuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
