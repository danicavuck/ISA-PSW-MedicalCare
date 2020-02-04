import { TestBed } from '@angular/core/testing';

import { ZahtevServiceService } from './zahtev-service.service';

describe('ZahtevServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ZahtevServiceService = TestBed.get(ZahtevServiceService);
    expect(service).toBeTruthy();
  });
});
