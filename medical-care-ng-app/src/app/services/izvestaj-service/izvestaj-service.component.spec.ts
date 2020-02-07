import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IzvestajServiceComponent } from './izvestaj-service.component';

describe('IzvestajServiceComponent', () => {
  let component: IzvestajServiceComponent;
  let fixture: ComponentFixture<IzvestajServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IzvestajServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IzvestajServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
