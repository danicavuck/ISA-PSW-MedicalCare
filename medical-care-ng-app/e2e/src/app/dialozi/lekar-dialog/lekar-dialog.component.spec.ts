import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LekarDialogComponent } from './lekar-dialog.component';

describe('LekarDialogComponent', () => {
  let component: LekarDialogComponent;
  let fixture: ComponentFixture<LekarDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LekarDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LekarDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
