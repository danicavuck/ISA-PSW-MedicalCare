import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IzmenaSaleComponent } from './izmena-sale.component';

describe('IzmenaSaleComponent', () => {
  let component: IzmenaSaleComponent;
  let fixture: ComponentFixture<IzmenaSaleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IzmenaSaleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IzmenaSaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
