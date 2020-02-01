import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahteviZaOdsustvoComponent } from './zahtevi-za-odsustvo.component';

describe('ZahteviZaOdsustvoComponent', () => {
  let component: ZahteviZaOdsustvoComponent;
  let fixture: ComponentFixture<ZahteviZaOdsustvoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ZahteviZaOdsustvoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ZahteviZaOdsustvoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
