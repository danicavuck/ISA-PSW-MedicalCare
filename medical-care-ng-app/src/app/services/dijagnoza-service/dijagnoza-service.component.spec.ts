import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DijagnozaServiceComponent } from './dijagnoza-service.component';

describe('DijagnozaServiceComponent', () => {
  let component: DijagnozaServiceComponent;
  let fixture: ComponentFixture<DijagnozaServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DijagnozaServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DijagnozaServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
