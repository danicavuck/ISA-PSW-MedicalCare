import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DodajDijagnozuComponent } from './dodaj-dijagnozu.component';

describe('DodajDijagnozuComponent', () => {
  let component: DodajDijagnozuComponent;
  let fixture: ComponentFixture<DodajDijagnozuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DodajDijagnozuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DodajDijagnozuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
