import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminKlinikeHomepageComponent } from './admin-klinike-homepage.component';

describe('AdminKlinikeHomepageComponent', () => {
  let component: AdminKlinikeHomepageComponent;
  let fixture: ComponentFixture<AdminKlinikeHomepageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminKlinikeHomepageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminKlinikeHomepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
