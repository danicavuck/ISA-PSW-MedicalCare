import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IzvestajOPregleduComponent } from './izvestaj-o-pregledu.component';

describe('IzvestajOPregleduComponent', () => {
  let component: IzvestajOPregleduComponent;
  let fixture: ComponentFixture<IzvestajOPregleduComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IzvestajOPregleduComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IzvestajOPregleduComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
