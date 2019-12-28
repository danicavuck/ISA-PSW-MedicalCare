import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PregledPacijenataComponent } from './pregled-pacijenata.component';

describe('PregledPacijenataComponent', () => {
  let component: PregledPacijenataComponent;
  let fixture: ComponentFixture<PregledPacijenataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PregledPacijenataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PregledPacijenataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
