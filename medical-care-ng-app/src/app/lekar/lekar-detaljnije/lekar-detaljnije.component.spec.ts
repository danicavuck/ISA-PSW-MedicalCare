import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LekarDetaljnijeComponent } from './lekar-detaljnije.component';

describe('LekarDetaljnijeComponent', () => {
  let component: LekarDetaljnijeComponent;
  let fixture: ComponentFixture<LekarDetaljnijeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LekarDetaljnijeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LekarDetaljnijeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
