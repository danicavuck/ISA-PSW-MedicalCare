import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KlinikaServiceComponent } from './klinika-service.component';

describe('KlinikaServiceComponent', () => {
  let component: KlinikaServiceComponent;
  let fixture: ComponentFixture<KlinikaServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KlinikaServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KlinikaServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
